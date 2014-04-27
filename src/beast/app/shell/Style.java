package beast.app.shell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Input;

@Description("Specifies all details of the style of a chart\n" + 
		"Chart style  (chartType, chartBackgroundColor, chartFontColor, chartPadding), \n"  + 
		"Chart title  (chartTitleFont, isChartTitleVisible, isChartTitleBoxVisible, chartTitleBoxBackgroundColor, chartTitleBoxBorderColor, chartTitlePadding), \n"  + 
		"Chart legend  (isLegendVisible, legendBackgroundColor, legendBorderColor, legendFont, legendPadding, legendSeriesLineLength, legendPosition), \n"  + 
		"Chart axes  (xAxisTitleVisible, yAxisTitleVisible, axisTitleFont, xAxisTicksVisible, yAxisTicksVisible, axisTickLabelsFont, axisTickMarkLength, axisTickPadding, axisTickMarksColor, axisTickMarksStroke, axisTickLabelsColor, isAxisTicksLineVisible, isAxisTicksMarksVisible, plotPadding, axisTitlePadding, xAxisTickMarkSpacingHint, yAxisTickMarkSpacingHint, isXAxisLogarithmic, isYAxisLogarithmic, xAxisMin, xAxisMax, yAxisMin, yAxisMax, axisTickSpaceRatio), \n"  + 
		"Chart plot area  (isPlotGridLinesVisible, plotBackgroundColor, plotBorderColor, isPlotBorderVisible, isPlotTicksMarksVisible, plotGridLinesColor, plotGridLinesStroke), \n"  + 
		"Bar charts  (barWidthPercentage, isBarsOverlapped), \n"  + 
		"Line, scatter, area charts  (markerSize), \n"  + 
		"Error bars  (errorBarsColor), and \n"  + 
		"Formatting  (datePattern, locale, timezone, normalDecimalPattern, scientificDecimalPattern)"  
)
public class Style extends BEASTObject {
	public Input<ChartTheme> themeInput = new Input<ChartTheme>("theme", "default theme for drawing a chart, one of " + 
			Arrays.toString(ChartTheme.values()), ChartTheme.XChart, ChartTheme.values());
	
	public Input<ChartType> chartTypeInput = new Input<ChartType>("chartType", "Chart style : chart type");
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
	public Input<LegendPosition> legendPositionInput = new Input<LegendPosition>("legendPosition", "Chart legend : legend position");
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

	public void initAndValidate() throws Exception {};
		
	public void setStyleOf(Chart chart) {
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
	}
}
