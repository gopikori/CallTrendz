/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

import android.content.Context;

import com.gopicreations.calltrendz.Constants.Type;

/**
 * The data set for call trendz graphs
 * @author gopi
 *
 */
public class BarChartDataset extends XYMultipleSeriesDataset {

  private Type chartType = null;
  private Context context = null;
  private int legendStringId = 0;
  
  public BarChartDataset(Type chartType, Context context, int legendStringId) {
    super();
    this.chartType = chartType;
    this.context = context;
    this.legendStringId = legendStringId;
  }
  
  /**
   * Set the data for this graph.
   * @param matrix the graph data matrix with multiple rows and two columns, 
   * each row representing (X,Y) coordinate
   */
  public void setData(int matrix[][]) {
    XYSeries series = new XYSeries(context.getString(legendStringId));
    for(int i=0; i<matrix.length; i++) {
      series.add(matrix[i][0], matrix[i][1]);
    }
    addSeries(series);
  }

}
