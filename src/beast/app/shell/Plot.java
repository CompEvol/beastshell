package beast.app.shell;



import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.internal.style.SeriesColorMarkerLineStyle;
import com.xeiam.xchart.internal.style.SeriesColorMarkerLineStyleCycler;
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
	public Input<String> chartTitleInput = new Input<String>("title","title of the chart");
	public Input<String> xAxisTitleInput = new Input<String>("xAxisTitle","title for the x-axis");
	public Input<String> yAxisTitleInput = new Input<String>("yAxisTitle","title for the y-axis");

	public Input<String> outputInput = new Input<String>("output","one of gif, png, bmp, jpg. Creates file /tmp/x.<ext>");
	
	static BEASTStudio studio = null;
	
	Chart chart = null;
	public Chart getChart() {return chart;}
	SeriesColorMarkerLineStyleCycler defaultStyle = new SeriesColorMarkerLineStyleCycler();
	
	
	@Override
	public void initAndValidate() throws Exception {
		Series series = seriesInput.get();
		add(series);	    
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
	
	public void add(Series series) throws IOException {
		boolean initial = false;
		List<Double> x;
		List<Double> y;
		List<Double> errors;
		x = series.xInput.get();
		y = series.yInput.get();
		errors = series.erroBarsInput.get();
		if (x.size() == 0) {
			x = new ArrayList<>();
			for (int i = 0; i < y.size(); i++) {
				x.add((double) i);
			}
		}
		
		if (chart == null) {
			initial = true;
			ChartTheme theme = styleInput.get().themeInput.get();
			ChartBuilder builder =	new ChartBuilder().xAxisTitle(series.xAxisInput.get()).yAxisTitle(series.yAxisInput.get()).width(600).height(400).theme(theme);
			if (chartTitleInput.get() != null) {
				builder = builder.title(chartTitleInput.get());
			}
			if (xAxisTitleInput.get() != null) {
				builder = builder.xAxisTitle(xAxisTitleInput.get());
			}
			if (yAxisTitleInput.get() != null) {
				builder = builder.yAxisTitle(yAxisTitleInput.get());
			}
			chart = builder.build();
			styleInput.get().setStyleOf(chart);
			
//			chart.getStyleManager().setXAxisMax(getMax(x));
//			chart.getStyleManager().setYAxisMax(getMax(y));
//			chart.getStyleManager().setXAxisMin(getMin(x));
//			chart.getStyleManager().setYAxisMin(getMin(y));
		}
		
		com.xeiam.xchart.Series series1;
		if (errors.size() ==0) { 
			series1 = chart.addSeries(series.seriesNameInput.get(), x, y);
		} else {
			series1 = chart.addSeries(series.seriesNameInput.get(), x, y, errors);			
		}
		SeriesColorMarkerLineStyle style0 =  defaultStyle.getNextSeriesColorMarkerLineStyle();
		
		series1.setLineColor(series.getLineColor() == null ? style0.getColor() : series.getLineColor());
	    series1.setLineStyle(series.getlineStyle() == null ? style0.getStroke() : series.getlineStyle());
	    series1.setMarkerColor(series.getMarkerColor() == null ? style0.getColor() :series.getMarkerColor());
	    series1.setMarker(series.getMarker());

	    if (initial) {
		    JPanel chartPanel = new XChartPanel(chart);
		    if (studio != null) {
		    	studio.plotPane.addChart(chart);
		    }
	    } else {
		    if (studio != null) {
		    	studio.plotPane.update();
		    }
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

}
