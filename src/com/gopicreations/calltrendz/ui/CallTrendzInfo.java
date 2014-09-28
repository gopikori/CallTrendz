/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gopicreations.calltrendz.Constants;
import com.gopicreations.calltrendz.R;

/**
 * The 'About' tab showing application information
 * @author gopi
 *
 */
public class CallTrendzInfo extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    Log.i(Constants.LOGGER_NAME,"Oncreate: Creating 'About' Tabs");
  }  

}
