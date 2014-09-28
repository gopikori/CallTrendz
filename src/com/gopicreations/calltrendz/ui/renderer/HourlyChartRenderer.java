/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui.renderer;

import android.content.Context;

import com.gopicreations.calltrendz.R;

/**
 * The renderer for the hourly chart
 * 
 * @author gopi
 *
 */
public class HourlyChartRenderer extends CallChartRenderer {

	/**
	 * Creates a hourly chart renderer
	 * @param context the application context
   * @param chartProps major chart type properties
	 */
	public HourlyChartRenderer(Context context, ChartProps chartProps) {
		super(context, chartProps);
		setChartTitle(context.getString(R.string.hourly_graph_title));
		setXTitle(context.getString(R.string.hourly_x_title));
		setYTitle(context.getString(R.string.hourly_y_title));
		
		//Align the things along X-axis properly
		setXAxisMin(-0.5);
		setXAxisMax(23.5);
		
		//Set X-Axis labels
		for(int i=0; i<24; i++) { //Make it human understandable hour of day
			addTextLabel(i, (i%12==0?12:i%12)+(i==0?"a":(i==12?"p":"")));
		}
	}

}
