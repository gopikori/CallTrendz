/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui;

import com.gopicreations.calltrendz.Constants;
import com.gopicreations.calltrendz.R;
import com.gopicreations.calltrendz.ui.renderer.ChartProps;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

/**
 * A helper to ease the intents creation
 * @author gopi
 *
 */
public class IntentHelper {

  /**
   * Create an intent for the incoming charts
   * @param context the application context
   * @return the intent to show the incoming charts
   */
  public static Intent createIncomingChartIntent(Context context) {
    Intent intent = new Intent(context, CallTrendzViewer.class);
    ChartProps chartProps = new ChartProps();
    chartProps.color = context.getResources().getColor(R.color.light_blue);
    chartProps.edgeColor = context.getResources().getColor(R.color.dark_blue);
    chartProps.callType = Constants.CALLTYPE_INCOMING;
    chartProps.legendStringId = R.string.incoming_graph_legend;
    intent.putExtra(Constants.CHARTPROPS, chartProps);
    return intent;
  }
  
  /**
   * Create an intent for the outgoing charts
   * @param context the application context
   * @return the intent to show the outgoing charts
   */
  public static Intent createOutgoingChartIntent(Context context) {
    Intent intent = new Intent(context, CallTrendzViewer.class);
    ChartProps chartProps = new ChartProps();
    chartProps.color = context.getResources().getColor(R.color.light_green);
    chartProps.edgeColor = context.getResources().getColor(R.color.dark_green);
    chartProps.callType = Constants.CALLTYPE_OUTGOING;
    chartProps.legendStringId = R.string.outgoing_graph_legend;
    intent.putExtra(Constants.CHARTPROPS, chartProps);
    return intent;
    
  }
  
  /**
   * Create an intent for the 'About' page
   * @param context the application context
   * @return the intent to show the application info
   */
  public static Intent createAboutIntent(Context context) {
    Intent intent = new Intent(context, CallTrendzInfo.class);
    return intent;
    
  }
}
