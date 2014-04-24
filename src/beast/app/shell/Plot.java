package beast.app.shell;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.XChartPanel;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Input;

@Description("Creates a chart of data")
public class Plot extends BEASTObject {
	public Input<List<Double>> xInput = new Input<List<Double>>("x","x-axis of data, if y is specified", new ArrayList<Double>());
	public Input<List<Double>> yInput = new Input<List<Double>>("y","y-axis of data, if specified", new ArrayList<Double>());
	public Input<String> seriesNameInput = new Input<String>("seriesName","name of the series", "series 1");
	public Input<String> xAxisInput = new Input<String>("xAxis","label of the x-axis", "X");
	public Input<String> yAxisInput = new Input<String>("yAxis","label of the y-axis", "Y");
	public Input<String> outputInput = new Input<String>("output","one of gif, png, bmp, jpg. Creates file /tmp/x.<ext>");
	
	static BEASTStudio studio = null;
	
	List<Double> x;
	List<Double> y;
	
	@Override
	public void initAndValidate() throws Exception {
		x = xInput.get();
		y = yInput.get();
		Chart chart = new ChartBuilder().xAxisTitle(xAxisInput.get()).yAxisTitle(yAxisInput.get()).width(600).height(400).build();
		
		chart.getStyleManager().setXAxisMax(getMax(x));
		chart.getStyleManager().setYAxisMax(getMax(y));
		chart.getStyleManager().setXAxisMin(getMin(x));
		chart.getStyleManager().setYAxisMin(getMin(y));
		chart.addSeries(seriesNameInput.get(), x, y);
	    JPanel chartPanel = new XChartPanel(chart);
	    if (studio != null) {
	    	studio.plotPane.removeAll();
	    	studio.plotPane.add(chartPanel);
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

}
