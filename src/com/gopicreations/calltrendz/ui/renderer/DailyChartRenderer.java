/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui.renderer;

import java.util.Calendar;

import android.content.Context;

import com.gopicreations.calltrendz.R;

/**
 * The renderer for the daily chart
 * 
 * @author gopi
 *
 */
public class DailyChartRenderer extends CallChartRenderer {

	/**
	 * Creates a daily chart renderer
	 * @param context the application context
   * @param chartProps major chart type properties
	 */
	public DailyChartRenderer(Context context, ChartProps chartProps) {
		super(context, chartProps);
		setChartTitle(context.getString(R.string.daily_graph_title));
		setXTitle(context.getString(R.string.daily_x_title));
		setYTitle(context.getString(R.string.daily_y_title));
		
		//Align the things along X-axis properly
		setXAxisMin(0.5);
		setXAxisMax(7.5);
		
	  //Set X-Axis labels
    addTextLabel(Calendar.MONDAY, context.getString(R.string.monday));
    addTextLabel(Calendar.TUESDAY, context.getString(R.string.tuesday));
    addTextLabel(Calendar.WEDNESDAY, context.getString(R.string.wednesday));
    addTextLabel(Calendar.THURSDAY, context.getString(R.string.thursday));
    addTextLabel(Calendar.FRIDAY, context.getString(R.string.friday));
    addTextLabel(Calendar.SATURDAY, context.getString(R.string.saturday));
    addTextLabel(Calendar.SUNDAY, context.getString(R.string.sunday));    
	}

}
