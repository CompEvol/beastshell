package beast.app.shell;


import java.util.List;

import javax.swing.JPanel;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.XChartPanel;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Input;
import beast.core.Input.Validate;

@Description("Creates a chart of data")
public class Plot extends BEASTObject {
	public Input<Series> seriesInput = new Input<Series>("series", "series to be be plotted initialy", Validate.REQUIRED);
	public Input<Style> styleInput = new Input<Style>("style", "style used for drawin the chart", new Style());
	public Input<String> outputInput = new Input<String>("output","one of gif, png, bmp, jpg. Creates file /tmp/x.<ext>");
	
	static BEASTStudio studio = null;
	
	List<Double> x;
	List<Double> y;
	Chart chart;
	
	@Override
	public void initAndValidate() throws Exception {
		Series series = seriesInput.get();
		x = series.xInput.get();
		y = series.yInput.get();
		ChartTheme theme = styleInput.get().themeInput.get();
		chart = new ChartBuilder().xAxisTitle(series.xAxisInput.get()).yAxisTitle(series.yAxisInput.get()).width(600).height(400).theme(theme).build();
		styleInput.get().setStyleOf(chart);
		
		chart.getStyleManager().setXAxisMax(getMax(x));
		chart.getStyleManager().setYAxisMax(getMax(y));
		chart.getStyleManager().setXAxisMin(getMin(x));
		chart.getStyleManager().setYAxisMin(getMin(y));
		
		com.xeiam.xchart.Series series1 = chart.addSeries(series.seriesNameInput.get(), x, y);
		series1.setLineColor(series.getLineColor());
	    series1.setLineStyle(series.getlineStyle());
	    series1.setMarkerColor(series.getMarkerColor());
	    series1.setMarker(series.getMarker());

		
	    JPanel chartPanel = new XChartPanel(chart);
	    if (studio != null) {
	    	//studio.plotPane.removeAll();
	    	studio.plotPane.addChart(chart);
	    	studio.plotPane.repaint();
	    }
	    if (outputInput.get() != null) {
	    	String format = outputInput.get().toLowerCase().trim();
	    	if (format.equals("png")) {
	    		BitmapEncoder.saveBitmap(chart, "/tmp/Sample_Chart", BitmapFormat.PNG);
	    	} else if (format.equals("jpg")) {
	    		BitmapEncoder.saveBitmap(chart, "/tmp/Sample_Chart", BitmapFormat.JPG);
	    	} else if (format.equals("bmp")) {
	    		BitmapEncoder.saveBitmap(chart, "/tmp/Sample_Chart", BitmapFormat.BMP);
	    	} else if (format.equals("gif")) {
	    		BitmapEncoder.saveBitmap(chart, "/tmp/Sample_Chart", BitmapFormat.GIF);
	    	} else {
	    		throw new RuntimeException("Unrecognised output format " + format + " expected one of gif, png, bmp, jpg.");
	    	}
	    }
	    
	}

	private double getMin(List<Double> x2) {
		double max = x2.get(0);
		for (double x : x2) {
			max = Math.max(max, x);
		}
		return max;
	}

	private double getMax(List<Double> x2) {
		double min = x2.get(0);
		for (double x : x2) {
			min = Math.min(min, x);
		}
		return min;
	}
	
	public void add(Series series) {
		com.xeiam.xchart.Series series1 = chart.addSeries(series.seriesNameInput.get(), series.xInput.get(), series.yInput.get());		
		series1.setLineColor(series.getLineColor());
	    series1.setLineStyle(series.getlineStyle());
	    series1.setMarkerColor(series.getMarkerColor());
	    series1.setMarker(series.getMarker());
	}

}
