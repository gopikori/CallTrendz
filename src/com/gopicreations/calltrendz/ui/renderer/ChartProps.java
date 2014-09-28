/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui.renderer;

import java.io.Serializable;

/**
 * The graph properties which are common for hourly/daily/monthly graphs but 
 * vary for major level chart types (incoming/outgoing etc)
 * 
 * @author gopi
 *
 */
public class ChartProps implements Serializable {
  
  public ChartProps(){};
  
  /**
   * Color of the bar in the bar chart
   */
  public int color;
  
  /**
   * Color of the edge of the bar chart used to produce the 3D effect 
   */
  public int edgeColor;
  
  /**
   * Id of the resource string that should be used with the legend shown
   * at the bottom of the chart 
   */
  public int legendStringId;
  
  /**
   * Type of the call. Constants.CALLTYPE_INCOMING or Constants.CALLTYPE_OUTGOING
   */
  public int callType;

}
