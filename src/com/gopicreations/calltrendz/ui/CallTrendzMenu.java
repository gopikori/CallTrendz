/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.gopicreations.calltrendz.Constants;
import com.gopicreations.calltrendz.R;

/**
 * 
 * The main navigation where the user can select the type of call graphs to 
 * be viewed.
 *  
 * @author gopi
 *
 */
public class CallTrendzMenu extends TabActivity {

  TabHost mTabHost = null;
	
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Log.i(Constants.LOGGER_NAME,"Oncreate: Creating Major Tabs");
    mTabHost = getTabHost();
    Intent incomingIntent = IntentHelper.createIncomingChartIntent(getApplicationContext());
    Intent outgoingIntent = IntentHelper.createOutgoingChartIntent(getApplicationContext());
    Intent aboutIntent = IntentHelper.createAboutIntent(getApplicationContext());
    mTabHost.addTab(mTabHost.newTabSpec("tab_incoming")
    		.setIndicator(getString(R.string.major_tab_title_incoming)).setContent(incomingIntent));
    mTabHost.addTab(mTabHost.newTabSpec("tab_outgoing")
    		.setIndicator(getString(R.string.major_tab_title_outgoing)).setContent(outgoingIntent));
    mTabHost.addTab(mTabHost.newTabSpec("tab_about")
    		.setIndicator(getString(R.string.major_tab_title_about)).setContent(aboutIntent));

    hackSizes();
    mTabHost.setCurrentTab(0);
    
  }  
  
  /**
   * Hack to change the tab size until android exposes the correct way to do this
   */
  private void hackSizes() {
  	int tabs = mTabHost.getTabWidget().getChildCount();
  	for(int i=0; i<tabs; i++) {
    	mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = Constants.TAB_HEIGHT_MAJOR;
  	}
  	mTabHost.getTabWidget().getChildAt(0).getLayoutParams().width = Constants.TAB_WIDTH_MAJOR;
  	mTabHost.getTabWidget().getChildAt(1).getLayoutParams().width = Constants.TAB_WIDTH_MAJOR;
  }
  
}
