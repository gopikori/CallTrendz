/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.gopicreations.calltrendz.Constants;
import com.gopicreations.calltrendz.R;
import com.gopicreations.calltrendz.Constants.Type;
import com.gopicreations.calltrendz.persistence.Database;
import com.gopicreations.calltrendz.ui.renderer.ChartProps;
import com.gopicreations.calltrendz.ui.renderer.DailyChartRenderer;
import com.gopicreations.calltrendz.ui.renderer.HourlyChartRenderer;
import com.gopicreations.calltrendz.ui.renderer.MonthlyChartRenderer;

/**
 * 
 * UI for the minor tabs that display graphs 
 * @author gopi
 *
 */
public class CallTrendzViewer extends TabActivity {

  TabHost mTabHost = null;
  private GraphicalView hourlyCallChartView; 
  private GraphicalView dailyCallChartView; 
  private GraphicalView monthlyCallChartView; 
  private ChartProps chartProps = null;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState);
    Log.i(Constants.LOGGER_NAME,"Oncreate");
    setContentView(R.layout.graphtab);
    chartProps = (ChartProps) getIntent().getExtras().get(Constants.CHARTPROPS);
      
    mTabHost = getTabHost();

    mTabHost.addTab(mTabHost.newTabSpec("tab_hourlycalltrendz")
    		.setIndicator(getString(R.string.minor_tab_title_hourly)).setContent(R.id.hourlychart));
    mTabHost.addTab(mTabHost.newTabSpec("tab_dailycalltrendz")
    		.setIndicator(getString(R.string.minor_tab_title_daily)).setContent(R.id.dailychart));
    mTabHost.addTab(mTabHost.newTabSpec("tab_monthlycalltrendz")
    		.setIndicator(getString(R.string.minor_tab_title_monthly)).setContent(R.id.monthlychart));

    hackSizes();
    mTabHost.setCurrentTab(0);

  }

  /**
   * Hack to change the tab size until android exposes the correct way to do this
   */
  private void hackSizes() {
  	int tabs = mTabHost.getTabWidget().getChildCount();
  	for(int i=0; i<tabs; i++) {
    	mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = Constants.TAB_HEIGHT_MINOR;
  	}
  }

	/* (non-Javadoc)
   * @see android.app.Activity#onStart()
   */
  protected void onStart(){
    super.onResume();
    Log.i(Constants.LOGGER_NAME,"Onstart");
    paintGraphs(true);
  }
  
  /* (non-Javadoc)
   * @see android.app.ActivityGroup#onResume()
   */
  protected void onResume() {
    super.onResume();
    Log.i(Constants.LOGGER_NAME,"Onresume");
    paintGraphs(false);
  }

  /**
   * Paint all the graphs 
   * @param forceDraw setting this to 'true' will force all the graphs to be re-drawn 
   * even if they were already painted.  
   */
  private void paintGraphs(boolean forceDraw) {
    Context context = getApplicationContext();
    Database db = new Database(context);
    
    updateChart(hourlyCallChartView, Type.HOURLY, forceDraw, db, context);
    updateChart(dailyCallChartView, Type.DAILY, forceDraw, db, context);
    updateChart(monthlyCallChartView, Type.MONTHLY, forceDraw, db, context);
    
  }
  
  /**
   * Updates the specified chart view 
   * @param view the view to be updated
   * @param type type of the graph
   * @param forceDraw setting this to 'true' will force all the graphs to be re-drawn 
   * even if they were already painted.  
   * @param db the database 
   * @param context the application context
   */
  private void updateChart(GraphicalView view, Type type, boolean forceDraw, Database db, Context context) {
    if (forceDraw || view == null) {
      Log.i(Constants.LOGGER_NAME,"Drawing " + type + " graphs");
      
      switch (type) {
      case HOURLY:
        updateHourlyChart(db, context);
	      break;
      case DAILY:
        updateDailyChart(db, context);
	      break;
      case MONTHLY:
        updateMonthlyChart(db, context);
	      break;
      }
      
    } else {
      Log.i(Constants.LOGGER_NAME,"Repainting " + type + " graphs");
      monthlyCallChartView.repaint();
    }
  }
  
  /**
   * Updates the hourly chart by fetching the data from the database, 
   * creating a view and putting the view in the layout 
   * @param db the database
   * @param context the application context
   */
  private void updateHourlyChart(Database db, Context context) {
  	//Choose the layout to paint the graph in
    LinearLayout layout = (LinearLayout) findViewById(R.id.hourlychart);
    //Get the data and prepare view
    BarChartDataset data = new BarChartDataset(Type.HOURLY, context, chartProps.legendStringId);
    data.setData(db.getHourwiseCalls(chartProps.callType));
    hourlyCallChartView = ChartFactory.getBarChartView(this, data, 
    		new HourlyChartRenderer(context,chartProps), BarChart.Type.DEFAULT);
    //Refresh UI
    updateView(hourlyCallChartView, layout);
  }

  /**
   * Updates the daily chart by fetching the data from the database, 
   * creating a view and putting the view in the layout 
   * @param db the database
   * @param context the application context
   */
  private void updateDailyChart(Database db, Context context) {
  	//Choose the layout to paint the graph in
    LinearLayout layout = (LinearLayout) findViewById(R.id.dailychart);
    //Get the data and prepare view
    BarChartDataset data = new BarChartDataset(Type.DAILY, context, chartProps.legendStringId);
    data.setData(db.getDaywiseCalls(chartProps.callType));
    dailyCallChartView = ChartFactory.getBarChartView(this, data, 
    		new DailyChartRenderer(context,chartProps), BarChart.Type.DEFAULT);
    //Refresh UI
    updateView(dailyCallChartView, layout);
  }

  /**
   * Updates the monthly chart by fetching the data from the database, 
   * creating a view and putting the view in the layout 
   * @param db the database
   * @param context the application context
   */
  private void updateMonthlyChart(Database db, Context context) {
  	//Choose the layout to paint the graph in
    LinearLayout layout = (LinearLayout) findViewById(R.id.monthlychart);
    //Get the data and prepare view
    BarChartDataset data = new BarChartDataset(Type.MONTHLY, context, chartProps.legendStringId);
    data.setData(db.getMonthwiseCalls(chartProps.callType));
    monthlyCallChartView = ChartFactory.getBarChartView(this, data, 
    		new MonthlyChartRenderer(context,chartProps), BarChart.Type.DEFAULT);
    //Refresh UI
    updateView(monthlyCallChartView, layout);
  }

	/**
   * Puts a view in the layout. Removes any already existing views in there.
   * @param view the new view to be set
   * @param layout the layout in which the view is to be set. 
   */
  private void updateView(GraphicalView view, LinearLayout layout) {
    layout.removeAllViews();
    layout.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    view.repaint();
  }


}