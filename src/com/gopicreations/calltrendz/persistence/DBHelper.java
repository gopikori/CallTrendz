/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * Helper for database operations.
 * Handles creation and upgrade of schemas
 * 
 * @author gopi
 *
 */
public class DBHelper extends SQLiteOpenHelper {

  private static String DB_NAME = "calltrendz.db";
  private static int DB_VERSION = 1;

  public DBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
   db.execSQL("CREATE TABLE props (name TEXT PRIMARY KEY, value TEXT);");
   db.execSQL("CREATE TABLE hourwisecalls (calltype INTEGER, slot INTEGER, count INTEGER);");
   db.execSQL("CREATE TABLE daywisecalls (calltype INTEGER, slot INTEGER, count INTEGER);");
   db.execSQL("CREATE TABLE monthwisecalls (calltype INTEGER, slot INTEGER, count INTEGER);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // TODO: Write upgrade db scripts

  }
}
