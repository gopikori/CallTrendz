/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.persistence;

import java.util.Hashtable;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.gopicreations.calltrendz.Constants;
import com.gopicreations.calltrendz.Utils;


/**
 * All the database related operations. 
 * 
 * @author gopi
 *
 */
public class Database {
  
  private DBHelper dbHelper = null;
  
  /**
   * Initiate the stuff required for database operations
   * @param context
   */
  public Database(Context context){
    dbHelper = new DBHelper(context);
    initialize();
  };

  /**
   * Initialize the database contents. 
   * @param database database instance to initialize
   */
  private void initialize() {
    String timeZero = getProp("timezero");
    Log.i(Constants.LOGGER_NAME, "Timezero 1:" + timeZero);
    if(timeZero == null) {
      Log.i(Constants.LOGGER_NAME, "TimeZero not found. Setting it.");
      setProp("timezero", ""+System.currentTimeMillis());
    }
    Log.i(Constants.LOGGER_NAME, "Timezero 2:" + getProp("timezero"));
  }
  
  
  /**
   * Get the number of calls per hour
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param hour hour of the day (0 to 23)
   * @return number of calls received in this hour of day. 
   */
  public int getCallCountForHour(int callType, int hour) {
    return getIntValue(Constants.TABLE_HOURWISECALLS, callType, hour);
  }
  
  /**
   * Get the number of calls per day
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param dow day of week. (1 to 7) 1=Sunday
   * @return number of calls received on this day of week.
   */
  public int getCallCountForDay(int callType, int dow) {
    return getIntValue(Constants.TABLE_DAYWISECALLS, callType, dow);
  }
  
  /**
   * Get the number of calls for specified month. 
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param month month of the year (1 to 12) 1 = January
   * @return number of calls received in this month.
   */
  public int getCallCountForMonth(int callType, int month) {
    return getIntValue(Constants.TABLE_MONTHWISECALLS, callType, month);
  }
  
  /**
   * Set the total calls count for specified hour of day
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param hour hour of day ( 0 to 23 )
   * @param count the value to set
   */
  public void setCallCountForHour(int callType, int hour, int count) {
    setIntValue(Constants.TABLE_HOURWISECALLS, callType, hour, count);
  }
  
  /**
   * Set the call count for the day 
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param day day of the week (1 to 7) 1=Sunday
   * @param count the value to set
   */
  public void setCallCountForDay(int callType, int day, int count) {
    setIntValue(Constants.TABLE_DAYWISECALLS, callType, day, count);
  }
  
  /**
   * Set the call count for the month
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param month month of the year (1 to 12) 1 = January
   * @param count the value to set
   */
  public void setCallCountForMonth(int callType, int month, int count) {
    setIntValue(Constants.TABLE_MONTHWISECALLS, callType, month, count);
  }

  public void updateCallCountForHour(int callType, int hour, int count) {
    updateIntValue(Constants.TABLE_HOURWISECALLS, callType, hour, count);
  }
  
  public void updateCallCountForDay(int callType, int day, int count) {
    updateIntValue(Constants.TABLE_DAYWISECALLS, callType, day, count);
  }
  
  public void updateCallCountForMonth(int callType, int month, int count) {
    updateIntValue(Constants.TABLE_MONTHWISECALLS, callType, month, count);
  }
  
  /**
   * Get a persistent property from the table
   * @param name name of property to get
   * @return String value of specified property, null if not found
   */
  public String getProp(String name) {
    String val = null;
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(Constants.TABLE_PROPS);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = qb.query(db, new String[]{"value"}, "name = '" + name +"'", null, null, null, null);
    if(c.getCount() > 0) {
      c.moveToFirst();
      val = c.getString(c.getColumnIndexOrThrow("value"));
    }
    closeDbAndCursor(db,c);
    return val;
  }
  
  /**
   * Persist a new property. Useful to create a new property, not for updating.  
   * @param name name of the property to set
   * @param val the String value to set
   * @return rowId of the newly created row.
   */
  public void setProp(String name, String val) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values  = new ContentValues();
    values.put("name",name);
    values.put("value",val);
    db.insert(Constants.TABLE_PROPS, "name", values);
    closeDb(db);
  }
  
  /**
   * Update an existing property. Will have no effect if no property exists with 
   * specified name.
   * @param name  name of the property to update
   * @param value the value of property to set. 
   */
  public void updateProp(String name, String value) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values  = new ContentValues();
    values.put("value",value);
    db.update(Constants.TABLE_PROPS, values, "name= ?", new String[]{name});
    closeDb(db);
  }
  
  /**
   * Retrieves from the database the matrix showing recorded calls per hour of day.
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @return matrix with 24 rows and 2 columns each row representing an hour of day.
   * First column indicates hour of day and the second column is number of calls
   */
  public int[][] getHourwiseCalls(int callType) {
    return Utils.mapToMatrix(getCallData(Constants.TABLE_HOURWISECALLS, callType), 24, 0);
  }
  
  /**
   * Retrieves from the database the matrix showing recorded calls per day of week.
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @return matrix with 24 rows and 2 columns each row representing a day of week.
   * First column indicates day of week and the second column is number of calls
   */
  public int[][] getDaywiseCalls(int callType) {
    return Utils.mapToMatrix(getCallData(Constants.TABLE_DAYWISECALLS, callType), 7, 1);
  }

  /**
   * Retrieves from the database the matrix showing recorded calls per month of year.
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @return matrix with 12 rows and 2 columns each row representing a month of year.
   * First column indicates month and the second column is number of calls
   */
  public int[][] getMonthwiseCalls(int callType) {
    return Utils.mapToMatrix(getCallData(Constants.TABLE_MONTHWISECALLS, callType), 12, 0);
  }
  
  //===================== PRIVATE API ================================
  
  /**
   * Retrieve call counts data 
   * @param table the call data table from which to retrieve the call data
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @return map of timeslots against the call count
   */
  private Map<Integer, Integer> getCallData(String table, int callType){
    Map <Integer, Integer> data = new Hashtable<Integer, Integer>();
    
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(table);
    qb.appendWhere(" calltype = " + callType);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = qb.query(db, new String[]{"slot","count"}, null, null, null, null, null);
    while(c.moveToNext()) {
      data.put(c.getInt(c.getColumnIndexOrThrow("slot")), c.getInt(c.getColumnIndexOrThrow("count")));
    }
    closeDbAndCursor(db,c);
    Log.i(Constants.LOGGER_NAME, "DATA=" + data);
    return data;
  }

  private int getIntValue(String table, int callType, int lookup) {
    int val = -1;
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(table);
    qb.appendWhere(" calltype = " + callType + " and slot = " + lookup);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = qb.query(db, new String[]{"count"}, null, null, null, null, null);
    if(c.getCount() > 0) {
      c.moveToFirst();
      val = c.getInt(c.getColumnIndexOrThrow("count"));
    }
    closeDbAndCursor(db,c);
    return val;
  }

  private void setIntValue(String table, int callType, int slot, int count) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values  = new ContentValues();
    values.put("calltype",callType);
    values.put("slot", slot);
    values.put("count",count);
    db.insert(table, "calltype", values);
    closeDb(db);
  }

  private void updateIntValue(String table, int callType, int slot, int count) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values  = new ContentValues();
    values.put("count",count);
    db.update(table, values, "calltype = ? and slot = ?", new String[]{""+callType, ""+slot});
    closeDb(db);
  }
  
  private void closeDb(SQLiteDatabase db) {
    if(db != null) db.close();
  };
  
  private void closeDbAndCursor(SQLiteDatabase db, Cursor cursor) {
    if(cursor != null) cursor.close();
    if(db != null) db.close();
  }
  
}
