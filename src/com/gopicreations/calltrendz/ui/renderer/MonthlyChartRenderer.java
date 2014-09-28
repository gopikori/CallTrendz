/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui.renderer;

import java.util.Calendar;

import android.content.Context;

import com.gopicreations.calltrendz.R;

/**
 * The renderer for the monthly chart
 * 
 * @author gopi
 *
 */
public class MonthlyChartRenderer extends CallChartRenderer {

	/**
	 * Creates a monthly chart renderer
	 * @param context the application context
   * @param chartProps major chart type properties
	 */
	public MonthlyChartRenderer(Context context, ChartProps chartProps) {
		super(context, chartProps);
		setChartTitle(context.getString(R.string.monthly_graph_title));
		setXTitle(context.getString(R.string.monthly_x_title));
		setYTitle(context.getString(R.string.monthly_y_title));
		
		//Align the things along X-axis properly
		setXAxisMin(-0.5);
		setXAxisMax(11.5);
		
		//Set X-Axis labels
    addTextLabel(Calendar.JANUARY, context.getString(R.string.january));
    addTextLabel(Calendar.FEBRUARY, context.getString(R.string.february));
    addTextLabel(Calendar.MARCH, context.getString(R.string.march));
    addTextLabel(Calendar.APRIL, context.getString(R.string.april));
    addTextLabel(Calendar.MAY, context.getString(R.string.may));
    addTextLabel(Calendar.JUNE, context.getString(R.string.june));
    addTextLabel(Calendar.JULY, context.getString(R.string.july));
    addTextLabel(Calendar.AUGUST, context.getString(R.string.august));
    addTextLabel(Calendar.SEPTEMBER, context.getString(R.string.september));
    addTextLabel(Calendar.OCTOBER, context.getString(R.string.october));
    addTextLabel(Calendar.NOVEMBER, context.getString(R.string.november));
    addTextLabel(Calendar.DECEMBER, context.getString(R.string.december));
	}

}
