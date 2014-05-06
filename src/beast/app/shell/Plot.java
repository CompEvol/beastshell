package beast.app.shell;



import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JPanel;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.internal.style.SeriesColorMarkerLineStyle;
import com.xeiam.xchart.internal.style.SeriesColorMarkerLineStyleCycler;
import com.xeiam.xchart.XChartPanel;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;

import beast.core.Description;
import beast.core.Input;

@Description("Creates a chart of data<br>" +
		"and specifies all details of the style of a chart<br>\n" + 
		"Chart style  (chartType, chartBackgroundColor, chartFontColor, chartPadding), <br>\n"  + 
		"Chart title  (chartTitleFont, isChartTitleVisible, isChartTitleBoxVisible, chartTitleBoxBackgroundColor, chartTitleBoxBorderColor, chartTitlePadding), <br>\n"  + 
		"Chart legend  (isLegendVisible, legendBackgroundColor, legendBorderColor, legendFont, legendPadding, legendSeriesLineLength, legendPosition), <br>\n"  + 
		"Chart axes  (xAxisTitleVisible, yAxisTitleVisible, axisTitleFont, xAxisTicksVisible, yAxisTicksVisible, axisTickLabelsFont, axisTickMarkLength, axisTickPadding, axisTickMarksColor, axisTickMarksStroke, axisTickLabelsColor, isAxisTicksLineVisible, isAxisTicksMarksVisible, plotPadding, axisTitlePadding, xAxisTickMarkSpacingHint, yAxisTickMarkSpacingHint, isXAxisLogarithmic, isYAxisLogarithmic, xAxisMin, xAxisMax, yAxisMin, yAxisMax, axisTickSpaceRatio), <br>\n"  + 
		"Chart plot area  (isPlotGridLinesVisible, plotBackgroundColor, plotBorderColor, isPlotBorderVisible, isPlotTicksMarksVisible, plotGridLinesColor, plotGridLinesStroke), \n"  + 
		"Bar charts  (barWidthPercentage, isBarsOverlapped), <br>\n"  + 
		"Line, scatter, area charts  (markerSize), <br>\n"  + 
		"Error bars  (errorBarsColor), and <br>\n"  + 
		"Formatting  (datePattern, locale, timezone, normalDecimalPattern, scientificDecimalPattern)<br>")
public class Plot extends Series {
//	public Input<Series> seriesInput = new Input<Series>("series", "series to be be plotted initialy", Validate.REQUIRED);
//	public Input<Style> styleInput = new Input<Style>("style", "style used for drawin the chart", new Style());
	public Input<String> chartTitleInput = new Input<String>("title","title of the chart");
	public Input<String> xAxisTitleInput = new Input<String>("xAxisTitle","title for the x-axis");
	public Input<String> yAxisTitleInput = new Input<String>("yAxisTitle","title for the y-axis");

	public Input<String> outputInput = new Input<String>("output","one of gif, png, bmp, jpg. Creates file /tmp/Sample_Chart.<ext>");

	
	// Specifies all details of the style of a chart
	public Input<ChartTheme> themeInput = new Input<ChartTheme>("theme", "default theme for drawing a chart, one of " + 
			Arrays.toString(ChartTheme.values()), ChartTheme.XChart, ChartTheme.values());
	
	public Input<ChartType> chartTypeInput = new Input<ChartType>("chartType", "Chart style : chart type, one of " + 
			Arrays.toString(ChartType.values()) + " (default Line)", ChartType.Line, ChartType.values());
	
	public Input<Color> chartBackgroundColorInput = new Input<Color>("chartBackgroundColor", "Chart style : chart background color");
	public Input<Color> chartFontColorInput = new Input<Color>("chartFontColor", "Chart style : chart font color");
	public Input<Integer> chartPaddingInput = new Input<Integer>("chartPadding", "Chart style : chart padding");

	public Input<Font> chartTitleFontInput = new Input<Font>("chartTitleFont", "Chart title : chart title font");
	public Input<Boolean> isChartTitleVisibleInput = new Input<Boolean>("isChartTitleVisible", "Chart title : is chart title visible");
	public Input<Boolean> isChartTitleBoxVisibleInput = new Input<Boolean>("isChartTitleBoxVisible", "Chart title : is chart title box visible");
	public Input<Color> chartTitleBoxBackgroundColorInput = new Input<Color>("chartTitleBoxBackgroundColor", "Chart title : chart title box background color");
	public Input<Color> chartTitleBoxBorderColorInput = new Input<Color>("chartTitleBoxBorderColor", "Chart title : chart title box border color");
	public Input<Integer> chartTitlePaddingInput = new Input<Integer>("chartTitlePadding", "Chart title : chart title padding");
	
	public Input<Boolean> isLegendVisibleInput = new Input<Boolean>("isLegendVisible", "Chart legend : is legend visible");
	public Input<Color> legendBackgroundColorInput = new Input<Color>("legendBackgroundColor", "Chart legend : legend background color");
	public Input<Color> legendBorderColorInput = new Input<Color>("legendBorderColor", "Chart legend : legend border color");
	public Input<Font> legendFontInput = new Input<Font>("legendFont", "Chart legend : legend font");
	public Input<Integer> legendPaddingInput = new Input<Integer>("legendPadding", "Chart legend : legend padding");
	public Input<Integer> legendSeriesLineLengthInput = new Input<Integer>("legendSeriesLineLength", "Chart legend : legend series line length");
	public Input<LegendPosition> legendPositionInput = new Input<LegendPosition>("legendPosition", "Chart legend : legend position, one of " + 
			Arrays.toString(LegendPosition.values()) + " (default OutsideE)", LegendPosition.OutsideE, LegendPosition.values());

	public Input<Boolean> xAxisTitleVisibleInput = new Input<Boolean>("xAxisTitleVisible", "Chart axes : x axis title visible");
	public Input<Boolean> yAxisTitleVisibleInput = new Input<Boolean>("yAxisTitleVisible", "Chart axes : y axis title visible");
	public Input<Font> axisTitleFontInput = new Input<Font>("axisTitleFont", "Chart axes : axis title font");
	public Input<Boolean> xAxisTicksVisibleInput = new Input<Boolean>("xAxisTicksVisible", "Chart axes : x axis ticks visible");
	public Input<Boolean> yAxisTicksVisibleInput = new Input<Boolean>("yAxisTicksVisible", "Chart axes : y axis ticks visible");
	public Input<Font> axisTickLabelsFontInput = new Input<Font>("axisTickLabelsFont", "Chart axes : axis tick labels font");
	public Input<Integer> axisTickMarkLengthInput = new Input<Integer>("axisTickMarkLength", "Chart axes : axis tick mark length");
	public Input<Integer> axisTickPaddingInput = new Input<Integer>("axisTickPadding", "Chart axes : axis tick padding");
	public Input<Color> axisTickMarksColorInput = new Input<Color>("axisTickMarksColor", "Chart axes : axis tick marks color");
	public Input<Stroke> axisTickMarksStrokeInput = new Input<Stroke>("axisTickMarksStroke", "Chart axes : axis tick marks stroke");
	public Input<Color> axisTickLabelsColorInput = new Input<Color>("axisTickLabelsColor", "Chart axes : axis tick labels color");
	public Input<Boolean> isAxisTicksLineVisibleInput = new Input<Boolean>("isAxisTicksLineVisible", "Chart axes : is axis ticks line visible");
	public Input<Boolean> isAxisTicksMarksVisibleInput = new Input<Boolean>("isAxisTicksMarksVisible", "Chart axes : is axis ticks marks visible");
	public Input<Integer> plotPaddingInput = new Input<Integer>("plotPadding", "Chart axes : plot padding");
	public Input<Integer> axisTitlePaddingInput = new Input<Integer>("axisTitlePadding", "Chart axes : axis title padding");
	public Input<Integer> xAxisTickMarkSpacingHintInput = new Input<Integer>("xAxisTickMarkSpacingHint", "Chart axes : x axis tick mark spacing hint");
	public Input<Integer> yAxisTickMarkSpacingHintInput = new Input<Integer>("yAxisTickMarkSpacingHint", "Chart axes : y axis tick mark spacing hint");
	public Input<Boolean> isXAxisLogarithmicInput = new Input<Boolean>("isXAxisLogarithmic", "Chart axes : is x axis logarithmic");
	public Input<Boolean> isYAxisLogarithmicInput = new Input<Boolean>("isYAxisLogarithmic", "Chart axes : is y axis logarithmic");
	public Input<Double> xAxisMinInput = new Input<Double>("xAxisMin", "Chart axes : x axis min");
	public Input<Double> xAxisMaxInput = new Input<Double>("xAxisMax", "Chart axes : x axis max");
	public Input<Double> yAxisMinInput = new Input<Double>("yAxisMin", "Chart axes : y axis min");
	public Input<Double> yAxisMaxInput = new Input<Double>("yAxisMax", "Chart axes : y axis max");
	public Input<Double> axisTickSpaceRatioInput = new Input<Double>("axisTickSpaceRatio", "Chart axes : axis tick space ratio");

	public Input<Boolean> isPlotGridLinesVisibleInput = new Input<Boolean>("isPlotGridLinesVisible", "Chart plot area : is plot grid lines visible");
	public Input<Color> plotBackgroundColorInput = new Input<Color>("plotBackgroundColor", "Chart plot area : plot background color");
	public Input<Color> plotBorderColorInput = new Input<Color>("plotBorderColor", "Chart plot area : plot border color");
	public Input<Boolean> isPlotBorderVisibleInput = new Input<Boolean>("isPlotBorderVisible", "Chart plot area : is plot border visible");
	public Input<Boolean> isPlotTicksMarksVisibleInput = new Input<Boolean>("isPlotTicksMarksVisible", "Chart plot area : is plot ticks marks visible");
	public Input<Color> plotGridLinesColorInput = new Input<Color>("plotGridLinesColor", "Chart plot area : plot grid lines color");
	public Input<Stroke> plotGridLinesStrokeInput = new Input<Stroke>("plotGridLinesStroke", "Chart plot area : plot grid lines stroke");
	
	public Input<Double> barWidthPercentageInput = new Input<Double>("barWidthPercentage", "Bar charts : bar width percentage");
	public Input<Boolean> isBarsOverlappedInput = new Input<Boolean>("isBarsOverlapped", "Bar charts : is bars overlapped");
	
	public Input<Integer> markerSizeInput = new Input<Integer>("markerSize", "Line, scatter, area charts : marker size");
	
	public Input<Color> errorBarsColorInput = new Input<Color>("errorBarsColor", "Error bars : error bars color");

	public Input<String> datePatternInput = new Input<String>("datePattern", "Formatting : date pattern");
	public Input<Locale> localeInput = new Input<Locale>("locale", "Formatting : locale");
	public Input<TimeZone> timezoneInput = new Input<TimeZone>("timezone", "Formatting : timezone");
	public Input<String> normalDecimalPatternInput = new Input<String>("normalDecimalPattern", "Formatting : normal decimal pattern");
	public Input<String> scientificDecimalPatternInput = new Input<String>("scientificDecimalPattern", "Formatting : scientific decimal pattern");

	// shorter named synonymous inputs
	public Input<Color> chartBackgroundColorInput2 = new Input<Color>("cbc", "Chart style : chart background color -- synonym with chartBackgroundColor");
	public Input<Color> chartFontColorInput2 = new Input<Color>("cfc", "Chart style : chart font color -- synonym with chartFontColor");
	public Input<Integer> chartPaddingInput2 = new Input<Integer>("cp", "Chart style : chart padding -- synonym with chartPadding");
	public Input<Font> chartTitleFontInput2 = new Input<Font>("ctf", "Chart title : chart title font -- synonym with chartTitleFont");
	public Input<Boolean> isChartTitleVisibleInput2 = new Input<Boolean>("ictv", "Chart title : is chart title visible -- synonym with isChartTitleVisible");
	public Input<Boolean> isChartTitleBoxVisibleInput2 = new Input<Boolean>("ictbv", "Chart title : is chart title box visible -- synonym with isChartTitleBoxVisible");
	public Input<Color> chartTitleBoxBackgroundColorInput2 = new Input<Color>("ctbbc", "Chart title : chart title box background color -- synonym with chartTitleBoxBackgroundColor");
	public Input<Color> chartTitleBoxBorderColorInput2 = new Input<Color>("ctbbco", "Chart title : chart title box border color -- synonym with chartTitleBoxBorderColor");
	public Input<Integer> chartTitlePaddingInput2 = new Input<Integer>("ctp", "Chart title : chart title padding -- synonym with chartTitlePadding");
	public Input<Boolean> isLegendVisibleInput2 = new Input<Boolean>("ilv", "Chart legend : is legend visible -- synonym with isLegendVisible");
	public Input<Color> legendBackgroundColorInput2 = new Input<Color>("lbc", "Chart legend : legend background color -- synonym with legendBackgroundColor");
	public Input<Color> legendBorderColorInput2 = new Input<Color>("lbco", "Chart legend : legend border color -- synonym with legendBorderColor");
	public Input<Font> legendFontInput2 = new Input<Font>("lf", "Chart legend : legend font -- synonym with legendFont");
	public Input<Integer> legendPaddingInput2 = new Input<Integer>("lp", "Chart legend : legend padding -- synonym with legendPadding");
	public Input<Integer> legendSeriesLineLengthInput2 = new Input<Integer>("lsll", "Chart legend : legend series line length -- synonym with legendSeriesLineLength");
	//public Input<LegendPosition> legendPositionInput2 = new Input<LegendPosition>("lpo", "Chart legend : legend position -- synonym with legendPosition");
	public Input<Boolean> xAxisTitleVisibleInput2 = new Input<Boolean>("xatv", "Chart axes : x axis title visible -- synonym with xAxisTitleVisible");
	public Input<Boolean> yAxisTitleVisibleInput2 = new Input<Boolean>("yatv", "Chart axes : y axis title visible -- synonym with yAxisTitleVisible");
	public Input<Font> axisTitleFontInput2 = new Input<Font>("atf", "Chart axes : axis title font -- synonym with axisTitleFont");
	public Input<Boolean> xAxisTicksVisibleInput2 = new Input<Boolean>("xatvi", "Chart axes : x axis ticks visible -- synonym with xAxisTicksVisible");
	public Input<Boolean> yAxisTicksVisibleInput2 = new Input<Boolean>("yatvi", "Chart axes : y axis ticks visible -- synonym with yAxisTicksVisible");
	public Input<Font> axisTickLabelsFontInput2 = new Input<Font>("atlf", "Chart axes : axis tick labels font -- synonym with axisTickLabelsFont");
	public Input<Integer> axisTickMarkLengthInput2 = new Input<Integer>("atml", "Chart axes : axis tick mark length -- synonym with axisTickMarkLength");
	public Input<Integer> axisTickPaddingInput2 = new Input<Integer>("atp", "Chart axes : axis tick padding -- synonym with axisTickPadding");
	public Input<Color> axisTickMarksColorInput2 = new Input<Color>("atmc", "Chart axes : axis tick marks color -- synonym with axisTickMarksColor");
	public Input<Stroke> axisTickMarksStrokeInput2 = new Input<Stroke>("atms", "Chart axes : axis tick marks stroke -- synonym with axisTickMarksStroke");
	public Input<Color> axisTickLabelsColorInput2 = new Input<Color>("atlc", "Chart axes : axis tick labels color -- synonym with axisTickLabelsColor");
	public Input<Boolean> isAxisTicksLineVisibleInput2 = new Input<Boolean>("iatlv", "Chart axes : is axis ticks line visible -- synonym with isAxisTicksLineVisible");
	public Input<Boolean> isAxisTicksMarksVisibleInput2 = new Input<Boolean>("iatmv", "Chart axes : is axis ticks marks visible -- synonym with isAxisTicksMarksVisible");
	public Input<Integer> plotPaddingInput2 = new Input<Integer>("pp", "Chart axes : plot padding -- synonym with plotPadding");
	public Input<Integer> axisTitlePaddingInput2 = new Input<Integer>("atpa", "Chart axes : axis title padding -- synonym with axisTitlePadding");
	public Input<Integer> xAxisTickMarkSpacingHintInput2 = new Input<Integer>("xatmsh", "Chart axes : x axis tick mark spacing hint -- synonym with xAxisTickMarkSpacingHint");
	public Input<Integer> yAxisTickMarkSpacingHintInput2 = new Input<Integer>("yatmsh", "Chart axes : y axis tick mark spacing hint -- synonym with yAxisTickMarkSpacingHint");
	public Input<Boolean> isXAxisLogarithmicInput2 = new Input<Boolean>("ixal", "Chart axes : is x axis logarithmic -- synonym with isXAxisLogarithmic");
	public Input<Boolean> isYAxisLogarithmicInput2 = new Input<Boolean>("iyal", "Chart axes : is y axis logarithmic -- synonym with isYAxisLogarithmic");
	public Input<Double> xAxisMinInput2 = new Input<Double>("xam", "Chart axes : x axis min -- synonym with xAxisMin");
	public Input<Double> xAxisMaxInput2 = new Input<Double>("xama", "Chart axes : x axis max -- synonym with xAxisMax");
	public Input<Double> yAxisMinInput2 = new Input<Double>("yam", "Chart axes : y axis min -- synonym with yAxisMin");
	public Input<Double> yAxisMaxInput2 = new Input<Double>("yama", "Chart axes : y axis max -- synonym with yAxisMax");
	public Input<Double> axisTickSpaceRatioInput2 = new Input<Double>("atsr", "Chart axes : axis tick space ratio -- synonym with axisTickSpaceRatio");
	public Input<Boolean> isPlotGridLinesVisibleInput2 = new Input<Boolean>("ipglv", "Chart plot area : is plot grid lines visible -- synonym with isPlotGridLinesVisible");
	public Input<Color> plotBackgroundColorInput2 = new Input<Color>("pbc", "Chart plot area : plot background color -- synonym with plotBackgroundColor");
	public Input<Color> plotBorderColorInput2 = new Input<Color>("pbco", "Chart plot area : plot border color -- synonym with plotBorderColor");
	public Input<Boolean> isPlotBorderVisibleInput2 = new Input<Boolean>("ipbv", "Chart plot area : is plot border visible -- synonym with isPlotBorderVisible");
	public Input<Boolean> isPlotTicksMarksVisibleInput2 = new Input<Boolean>("iptmv", "Chart plot area : is plot ticks marks visible -- synonym with isPlotTicksMarksVisible");
	public Input<Color> plotGridLinesColorInput2 = new Input<Color>("pglc", "Chart plot area : plot grid lines color -- synonym with plotGridLinesColor");
	public Input<Stroke> plotGridLinesStrokeInput2 = new Input<Stroke>("pgls", "Chart plot area : plot grid lines stroke -- synonym with plotGridLinesStroke");
	public Input<Double> barWidthPercentageInput2 = new Input<Double>("bwp", "Bar charts : bar width percentage -- synonym with barWidthPercentage");
	public Input<Boolean> isBarsOverlappedInput2 = new Input<Boolean>("ibo", "Bar charts : is bars overlapped -- synonym with isBarsOverlapped");
	public Input<Integer> markerSizeInput2 = new Input<Integer>("ms", "Line, scatter, area charts : marker size -- synonym with markerSize");
	public Input<Color> errorBarsColorInput2 = new Input<Color>("ebc", "Error bars : error bars color -- synonym with errorBarsColor");
	public Input<String> datePatternInput2 = new Input<String>("dp", "Formatting : date pattern -- synonym with datePattern");
	public Input<Locale> localeInput2 = new Input<Locale>("l", "Formatting : locale -- synonym with locale");
	public Input<TimeZone> timezoneInput2 = new Input<TimeZone>("t", "Formatting : timezone -- synonym with timezone");
	public Input<String> normalDecimalPatternInput2 = new Input<String>("ndp", "Formatting : normal decimal pattern -- synonym with normalDecimalPattern");
	public Input<String> scientificDecimalPatternInput2 = new Input<String>("sdp", "Formatting : scientific decimal pattern -- synonym with scientificDecimalPattern");
	
	
	
	
	static BEASTStudio studio = null;
	
	Chart chart = null;
	public Chart getChart() {return chart;}
	SeriesColorMarkerLineStyleCycler defaultStyle = new SeriesColorMarkerLineStyleCycler();
	
	
	@Override
	public void initAndValidate() throws Exception {
		add(this);	    
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
		List<Object> x;
		List<Number> y;
		List<Double> errors;
		x = series.xInput.get();
		if (x.size() == 1 && x.get(0) instanceof List) {
			x = (List) x.get(0);
		}
		y = series.yInput.get();
		errors = series.erroBarsInput.get();
		if (x.size() == 0) {
			x = new ArrayList<>();
			for (int i = 0; i < y.size(); i++) {
				x.add(new Double(i));
			}
		}
		if (x.get(0) instanceof Number) {
			// convert to Double
			List<Object> list = new ArrayList<>();
			for (Object o : x) {
				if (o instanceof Integer){ 
					list.add(new Double((Integer) o));
				} else {
					list.add(o);
				}
			}
			x = list;
		}
		
		
		
		if (chart == null) {
			initial = true;
			ChartTheme theme = themeInput.get();
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
			setStyleOfChart();
			
//			chart.getStyleManager().setXAxisMax(getMax(x));
//			chart.getStyleManager().setYAxisMax(getMax(y));
//			chart.getStyleManager().setXAxisMin(getMin(x));
//			chart.getStyleManager().setYAxisMin(getMin(y));
		}
		
		com.xeiam.xchart.Series series1;
		String name = (seriesNameInput.get() != null ? seriesNameInput.get() : "series" + (initial ? "1" : chart.getSeriesMap().size() + 1));
		if (errors.size() ==0) { 
			series1 = chart.addSeries(name, x, y);
		} else {
			series1 = chart.addSeries(name, x, y, errors);			
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

	public void setStyleOfChart() {
		StyleManager style = chart.getStyleManager();
		if (chartTypeInput.get() != null) {
		    style.setChartType(chartTypeInput.get());
		}
		if (chartBackgroundColorInput.get() != null) {
		    style.setChartBackgroundColor(chartBackgroundColorInput.get());
		}
		if (chartFontColorInput.get() != null) {
		    style.setChartFontColor(chartFontColorInput.get());
		}
		if (chartPaddingInput.get() != null) {
		    style.setChartPadding(chartPaddingInput.get());
		}
		if (chartTitleFontInput.get() != null) {
		    style.setChartTitleFont(chartTitleFontInput.get());
		}
		if (isChartTitleVisibleInput.get() != null) {
		    style.setChartTitleVisible(isChartTitleVisibleInput.get());
		}
		if (isChartTitleBoxVisibleInput.get() != null) {
		    style.setChartTitleBoxVisible(isChartTitleBoxVisibleInput.get());
		}
		if (chartTitleBoxBackgroundColorInput.get() != null) {
		    style.setChartTitleBoxBackgroundColor(chartTitleBoxBackgroundColorInput.get());
		}
		if (chartTitleBoxBorderColorInput.get() != null) {
		    style.setChartTitleBoxBorderColor(chartTitleBoxBorderColorInput.get());
		}
		if (chartTitlePaddingInput.get() != null) {
		    style.setChartTitlePadding(chartTitlePaddingInput.get());
		}
		if (isLegendVisibleInput.get() != null) {
		    style.setLegendVisible(isLegendVisibleInput.get());
		}
		if (legendBackgroundColorInput.get() != null) {
		    style.setLegendBackgroundColor(legendBackgroundColorInput.get());
		}
		if (legendBorderColorInput.get() != null) {
		    style.setLegendBorderColor(legendBorderColorInput.get());
		}
		if (legendFontInput.get() != null) {
		    style.setLegendFont(legendFontInput.get());
		}
		if (legendPaddingInput.get() != null) {
		    style.setLegendPadding(legendPaddingInput.get());
		}
		if (legendSeriesLineLengthInput.get() != null) {
		    style.setLegendSeriesLineLength(legendSeriesLineLengthInput.get());
		}
		if (legendPositionInput.get() != null) {
		    style.setLegendPosition(legendPositionInput.get());
		}
		if (xAxisTitleVisibleInput.get() != null) {
		    style.setXAxisTitleVisible(xAxisTitleVisibleInput.get());
		}
		if (yAxisTitleVisibleInput.get() != null) {
		    style.setYAxisTitleVisible(yAxisTitleVisibleInput.get());
		}
		if (axisTitleFontInput.get() != null) {
		    style.setAxisTitleFont(axisTitleFontInput.get());
		}
		if (xAxisTicksVisibleInput.get() != null) {
		    style.setXAxisTicksVisible(xAxisTicksVisibleInput.get());
		}
		if (yAxisTicksVisibleInput.get() != null) {
		    style.setYAxisTicksVisible(yAxisTicksVisibleInput.get());
		}
		if (axisTickLabelsFontInput.get() != null) {
		    style.setAxisTickLabelsFont(axisTickLabelsFontInput.get());
		}
		if (axisTickMarkLengthInput.get() != null) {
		    style.setAxisTickMarkLength(axisTickMarkLengthInput.get());
		}
		if (axisTickPaddingInput.get() != null) {
		    style.setAxisTickPadding(axisTickPaddingInput.get());
		}
		if (axisTickMarksColorInput.get() != null) {
		    style.setAxisTickMarksColor(axisTickMarksColorInput.get());
		}
		if (axisTickMarksStrokeInput.get() != null) {
		    style.setAxisTickMarksStroke(axisTickMarksStrokeInput.get());
		}
		if (axisTickLabelsColorInput.get() != null) {
		    style.setAxisTickLabelsColor(axisTickLabelsColorInput.get());
		}
		if (isAxisTicksLineVisibleInput.get() != null) {
		    style.setAxisTicksLineVisible(isAxisTicksLineVisibleInput.get());
		}
		if (isAxisTicksMarksVisibleInput.get() != null) {
		    style.setAxisTicksMarksVisible(isAxisTicksMarksVisibleInput.get());
		}
		if (plotPaddingInput.get() != null) {
		    style.setPlotPadding(plotPaddingInput.get());
		}
		if (axisTitlePaddingInput.get() != null) {
		    style.setAxisTitlePadding(axisTitlePaddingInput.get());
		}
		if (xAxisTickMarkSpacingHintInput.get() != null) {
		    style.setXAxisTickMarkSpacingHint(xAxisTickMarkSpacingHintInput.get());
		}
		if (yAxisTickMarkSpacingHintInput.get() != null) {
		    style.setYAxisTickMarkSpacingHint(yAxisTickMarkSpacingHintInput.get());
		}
		if (isXAxisLogarithmicInput.get() != null) {
		    style.setXAxisLogarithmic(isXAxisLogarithmicInput.get());
		}
		if (isYAxisLogarithmicInput.get() != null) {
		    style.setYAxisLogarithmic(isYAxisLogarithmicInput.get());
		}
		if (xAxisMinInput.get() != null) {
		    style.setXAxisMin(xAxisMinInput.get());
		}
		if (xAxisMaxInput.get() != null) {
		    style.setXAxisMax(xAxisMaxInput.get());
		}
		if (yAxisMinInput.get() != null) {
		    style.setYAxisMin(yAxisMinInput.get());
		}
		if (yAxisMaxInput.get() != null) {
		    style.setYAxisMax(yAxisMaxInput.get());
		}
		if (axisTickSpaceRatioInput.get() != null) {
		    style.setAxisTickSpaceRatio(axisTickSpaceRatioInput.get());
		}
		if (isPlotGridLinesVisibleInput.get() != null) {
		    style.setPlotGridLinesVisible(isPlotGridLinesVisibleInput.get());
		}
		if (plotBackgroundColorInput.get() != null) {
		    style.setPlotBackgroundColor(plotBackgroundColorInput.get());
		}
		if (plotBorderColorInput.get() != null) {
		    style.setPlotBorderColor(plotBorderColorInput.get());
		}
		if (isPlotBorderVisibleInput.get() != null) {
		    style.setPlotBorderVisible(isPlotBorderVisibleInput.get());
		}
		if (isPlotTicksMarksVisibleInput.get() != null) {
		    style.setPlotTicksMarksVisible(isPlotTicksMarksVisibleInput.get());
		}
		if (plotGridLinesColorInput.get() != null) {
		    style.setPlotGridLinesColor(plotGridLinesColorInput.get());
		}
		if (plotGridLinesStrokeInput.get() != null) {
		    style.setPlotGridLinesStroke(plotGridLinesStrokeInput.get());
		}
		if (barWidthPercentageInput.get() != null) {
		    style.setBarWidthPercentage(barWidthPercentageInput.get());
		}
		if (isBarsOverlappedInput.get() != null) {
		    style.setBarsOverlapped(isBarsOverlappedInput.get());
		}
		if (markerSizeInput.get() != null) {
		    style.setMarkerSize(markerSizeInput.get());
		}
		if (errorBarsColorInput.get() != null) {
		    style.setErrorBarsColor(errorBarsColorInput.get());
		}
		if (datePatternInput.get() != null) {
		    style.setDatePattern(datePatternInput.get());
		}
		if (localeInput.get() != null) {
		    style.setLocale(localeInput.get());
		}
		if (timezoneInput.get() != null) {
		    style.setTimezone(timezoneInput.get());
		}
		if (normalDecimalPatternInput.get() != null) {
		    style.setNormalDecimalPattern(normalDecimalPatternInput.get());
		}
		if (scientificDecimalPatternInput.get() != null) {
		    style.setScientificDecimalPattern(scientificDecimalPatternInput.get());
		}
		
		if (chartBackgroundColorInput2.get() != null) {
		    style.setChartBackgroundColor(chartBackgroundColorInput2.get());
		}
		if (chartFontColorInput2.get() != null) {
		    style.setChartFontColor(chartFontColorInput2.get());
		}
		if (chartPaddingInput2.get() != null) {
		    style.setChartPadding(chartPaddingInput2.get());
		}
		if (chartTitleFontInput2.get() != null) {
		    style.setChartTitleFont(chartTitleFontInput2.get());
		}
		if (isChartTitleVisibleInput2.get() != null) {
		    style.setChartTitleVisible(isChartTitleVisibleInput2.get());
		}
		if (isChartTitleBoxVisibleInput2.get() != null) {
		    style.setChartTitleBoxVisible(isChartTitleBoxVisibleInput2.get());
		}
		if (chartTitleBoxBackgroundColorInput2.get() != null) {
		    style.setChartTitleBoxBackgroundColor(chartTitleBoxBackgroundColorInput2.get());
		}
		if (chartTitleBoxBorderColorInput2.get() != null) {
		    style.setChartTitleBoxBorderColor(chartTitleBoxBorderColorInput2.get());
		}
		if (chartTitlePaddingInput2.get() != null) {
		    style.setChartTitlePadding(chartTitlePaddingInput2.get());
		}
		if (isLegendVisibleInput2.get() != null) {
		    style.setLegendVisible(isLegendVisibleInput2.get());
		}
		if (legendBackgroundColorInput2.get() != null) {
		    style.setLegendBackgroundColor(legendBackgroundColorInput2.get());
		}
		if (legendBorderColorInput2.get() != null) {
		    style.setLegendBorderColor(legendBorderColorInput2.get());
		}
		if (legendFontInput2.get() != null) {
		    style.setLegendFont(legendFontInput2.get());
		}
		if (legendPaddingInput2.get() != null) {
		    style.setLegendPadding(legendPaddingInput2.get());
		}
		if (legendSeriesLineLengthInput2.get() != null) {
		    style.setLegendSeriesLineLength(legendSeriesLineLengthInput2.get());
		}
//		if (legendPositionInput2.get() != null) {
//		    style.setLegendPosition(legendPositionInput2.get());
//		}
		if (xAxisTitleVisibleInput2.get() != null) {
		    style.setXAxisTitleVisible(xAxisTitleVisibleInput2.get());
		}
		if (yAxisTitleVisibleInput2.get() != null) {
		    style.setYAxisTitleVisible(yAxisTitleVisibleInput2.get());
		}
		if (axisTitleFontInput2.get() != null) {
		    style.setAxisTitleFont(axisTitleFontInput2.get());
		}
		if (xAxisTicksVisibleInput2.get() != null) {
		    style.setXAxisTicksVisible(xAxisTicksVisibleInput2.get());
		}
		if (yAxisTicksVisibleInput2.get() != null) {
		    style.setYAxisTicksVisible(yAxisTicksVisibleInput2.get());
		}
		if (axisTickLabelsFontInput2.get() != null) {
		    style.setAxisTickLabelsFont(axisTickLabelsFontInput2.get());
		}
		if (axisTickMarkLengthInput2.get() != null) {
		    style.setAxisTickMarkLength(axisTickMarkLengthInput2.get());
		}
		if (axisTickPaddingInput2.get() != null) {
		    style.setAxisTickPadding(axisTickPaddingInput2.get());
		}
		if (axisTickMarksColorInput2.get() != null) {
		    style.setAxisTickMarksColor(axisTickMarksColorInput2.get());
		}
		if (axisTickMarksStrokeInput2.get() != null) {
		    style.setAxisTickMarksStroke(axisTickMarksStrokeInput2.get());
		}
		if (axisTickLabelsColorInput2.get() != null) {
		    style.setAxisTickLabelsColor(axisTickLabelsColorInput2.get());
		}
		if (isAxisTicksLineVisibleInput2.get() != null) {
		    style.setAxisTicksLineVisible(isAxisTicksLineVisibleInput2.get());
		}
		if (isAxisTicksMarksVisibleInput2.get() != null) {
		    style.setAxisTicksMarksVisible(isAxisTicksMarksVisibleInput2.get());
		}
		if (plotPaddingInput2.get() != null) {
		    style.setPlotPadding(plotPaddingInput2.get());
		}
		if (axisTitlePaddingInput2.get() != null) {
		    style.setAxisTitlePadding(axisTitlePaddingInput2.get());
		}
		if (xAxisTickMarkSpacingHintInput2.get() != null) {
		    style.setXAxisTickMarkSpacingHint(xAxisTickMarkSpacingHintInput2.get());
		}
		if (yAxisTickMarkSpacingHintInput2.get() != null) {
		    style.setYAxisTickMarkSpacingHint(yAxisTickMarkSpacingHintInput2.get());
		}
		if (isXAxisLogarithmicInput2.get() != null) {
		    style.setXAxisLogarithmic(isXAxisLogarithmicInput2.get());
		}
		if (isYAxisLogarithmicInput2.get() != null) {
		    style.setYAxisLogarithmic(isYAxisLogarithmicInput2.get());
		}
		if (xAxisMinInput2.get() != null) {
		    style.setXAxisMin(xAxisMinInput2.get());
		}
		if (xAxisMaxInput2.get() != null) {
		    style.setXAxisMax(xAxisMaxInput2.get());
		}
		if (yAxisMinInput2.get() != null) {
		    style.setYAxisMin(yAxisMinInput2.get());
		}
		if (yAxisMaxInput2.get() != null) {
		    style.setYAxisMax(yAxisMaxInput2.get());
		}
		if (axisTickSpaceRatioInput2.get() != null) {
		    style.setAxisTickSpaceRatio(axisTickSpaceRatioInput2.get());
		}
		if (isPlotGridLinesVisibleInput2.get() != null) {
		    style.setPlotGridLinesVisible(isPlotGridLinesVisibleInput2.get());
		}
		if (plotBackgroundColorInput2.get() != null) {
		    style.setPlotBackgroundColor(plotBackgroundColorInput2.get());
		}
		if (plotBorderColorInput2.get() != null) {
		    style.setPlotBorderColor(plotBorderColorInput2.get());
		}
		if (isPlotBorderVisibleInput2.get() != null) {
		    style.setPlotBorderVisible(isPlotBorderVisibleInput2.get());
		}
		if (isPlotTicksMarksVisibleInput2.get() != null) {
		    style.setPlotTicksMarksVisible(isPlotTicksMarksVisibleInput2.get());
		}
		if (plotGridLinesColorInput2.get() != null) {
		    style.setPlotGridLinesColor(plotGridLinesColorInput2.get());
		}
		if (plotGridLinesStrokeInput2.get() != null) {
		    style.setPlotGridLinesStroke(plotGridLinesStrokeInput2.get());
		}
		if (barWidthPercentageInput2.get() != null) {
		    style.setBarWidthPercentage(barWidthPercentageInput2.get());
		}
		if (isBarsOverlappedInput2.get() != null) {
		    style.setBarsOverlapped(isBarsOverlappedInput2.get());
		}
		if (markerSizeInput2.get() != null) {
		    style.setMarkerSize(markerSizeInput2.get());
		}
		if (errorBarsColorInput2.get() != null) {
		    style.setErrorBarsColor(errorBarsColorInput2.get());
		}
		if (datePatternInput2.get() != null) {
		    style.setDatePattern(datePatternInput2.get());
		}
		if (localeInput2.get() != null) {
		    style.setLocale(localeInput2.get());
		}
		if (timezoneInput2.get() != null) {
		    style.setTimezone(timezoneInput2.get());
		}
		if (normalDecimalPatternInput2.get() != null) {
		    style.setNormalDecimalPattern(normalDecimalPatternInput2.get());
		}
		if (scientificDecimalPatternInput2.get() != null) {
		    style.setScientificDecimalPattern(scientificDecimalPatternInput2.get());
		}
	}
}
