/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui.renderer;

import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

/**
 * A singleton defining all properties for rendering call trendz graphs. 
 * All graphs will use same renderer so that they look similar. 
 * 
 * @author gopi
 *
 */
public abstract class CallChartRenderer extends XYMultipleSeriesRenderer {


  private Context context = null;
  private ChartProps chartProps = null;;
  
  /**
   * Creates a renderer for call trendz chart
   * @param context the application context
   * @param chartProps major chart type properties
   * 
   */
  public CallChartRenderer(Context context, ChartProps chartProps) {
    super();
    this.context = context;
    this.chartProps = chartProps;
    init();
  }

  /**
   * Set all the base properties
   */
	private void init() {
		
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(chartProps!=null ? chartProps.color : Color.rgb(164, 202, 72));
		r.setEdgeColor(chartProps!=null ? chartProps.edgeColor : Color.rgb(84, 111, 0));
		r.setEdgeWidth(2);
		addSeriesRenderer(r);
		
		//Set colors
		setAxesColor(Color.DKGRAY);
		setLabelsColor(Color.LTGRAY);
		setBackgroundColor(Color.BLACK);
		
		//Enable/disable display stuff
		setDisplayChartValues(false);;
		setShowGrid(true);
		setShowAxes(true);
		setShowLabels(true);

		//Disable grid lables on X axis
		setXLabels(-1);
	}

	/**
	 * Get the application context 
	 * @return application context
	 */
	protected Context getContext() {
  	return context;
  }

}
