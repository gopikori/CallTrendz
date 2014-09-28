/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.gopicreations.calltrendz.persistence.Database;

/**
 * 
 * Receives the phone state events from the phone module and processes the information
 * to populate the required data for generating hourly/daily/monthly graphs. 
 * Records the data for all incoming and outgoing calls. 
 * 
 * @author gopi
 *
 */
public class CallTrendzTracker extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(Constants.LOGGER_NAME, "Intent Action:" + intent.getAction());
    Bundle bundle = intent.getExtras();
    Set<String> keys = bundle.keySet();
    for (String key : keys) {
      Log.i(Constants.LOGGER_NAME, key + ":" + bundle.getString(key));
    }
    //Get the database
    Database database = new Database(context);
    
    if(intent.getAction().equals("android.intent.action.PHONE_STATE")) { //Process incoming calls
      String callState = bundle.getString(TelephonyManager.EXTRA_STATE);
      if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
        Log.i(Constants.LOGGER_NAME, "Noticed incoming call from number "+ bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
        updateCallCounts(Constants.CALLTYPE_INCOMING, database);
      }
    } else if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) { //Process outgoing calls.
      Log.i(Constants.LOGGER_NAME, "Noticed outgoing call to number "+ bundle.getString("android.intent.extra.PHONE_NUMBER"));
      updateCallCounts(Constants.CALLTYPE_OUTGOING, database);
    }
  }

  /**
   * 
   * This should be called to update the call counts in the database when 
   * an incoming call is detected of an outgoing call is seen.
   * 
   * @param callType Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   * @param database the reference to database.
   */
  private void updateCallCounts(int callType, Database database) {
    Calendar currentTime = Calendar.getInstance();
    currentTime.setTime(new Date());
    int currHourOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
    int currDayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);
    int currMonthOfYear = currentTime.get(Calendar.MONTH);
    
    int hourlyCount = database.getCallCountForHour(callType, currHourOfDay);
    Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: hourlyCount=" + hourlyCount);
    if (hourlyCount == -1) {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: setting count for currHourOfDay=" + currHourOfDay);
      database.setCallCountForHour(callType, currHourOfDay, 1);
    } else {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: updating count for currHourOfDay=" + currHourOfDay);
      database.updateCallCountForHour(callType, currHourOfDay, hourlyCount+1);
    }
    
    int dayilyCount = database.getCallCountForDay(callType, currDayOfWeek);
    Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: dayilyCount=" + dayilyCount);
    if (dayilyCount == -1) {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: setting count for currDayOfWeek=" + currDayOfWeek);
      database.setCallCountForDay(callType, currDayOfWeek, 1);
    } else {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: updating count for currDayOfWeek=" + currDayOfWeek);
      database.updateCallCountForDay(callType, currDayOfWeek, dayilyCount + 1);
    }
    
    int monthlyCount = database.getCallCountForMonth(callType, currMonthOfYear);
    Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: monthlyCount=" + monthlyCount);
    if (monthlyCount == -1) {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: setting count for currMonthOfYear=" + currMonthOfYear);
      database.setCallCountForMonth(callType, currMonthOfYear, 1);
    } else {
      Log.i(Constants.LOGGER_NAME, "CALLCOUNTUPDATER: updating count for currMonthOfYear=" + currMonthOfYear);
      database.updateCallCountForMonth(callType, currMonthOfYear, monthlyCount+1);
    }
    
  }

}
