/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */

package com.gopicreations.calltrendz;

/**
 * Global constants for the CallTrendz
 * @author gopi
 *
 */
public class Constants {
  
  public static enum Type {HOURLY, DAILY, MONTHLY};

  public static String LOGGER_NAME = "CALL_TRENDZ####";
  
  public static String CHARTPROPS = "CHARTPROPS";
  
  public static int TAB_HEIGHT_MAJOR = 60;
  public static int TAB_HEIGHT_MINOR = 40;
  public static int TAB_WIDTH_MAJOR = 70;
  
  public static int CALLTYPE_INCOMING = 1;
  public static int CALLTYPE_OUTGOING = 2;
  
  public static String TABLE_HOURWISECALLS="hourwisecalls";
  public static String TABLE_DAYWISECALLS="daywisecalls";
  public static String TABLE_MONTHWISECALLS="monthwisecalls";
  public static String TABLE_PROPS="props";
  
  
}
