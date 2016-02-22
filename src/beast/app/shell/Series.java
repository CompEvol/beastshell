package beast.app.shell;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xeiam.xchart.SeriesLineStyle;
import com.xeiam.xchart.SeriesMarker;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Input;
import beast.core.Input.Validate;

@Description("Contains a series to be used in a plot")
public class Series extends BEASTObject {
	public Input<List<Number>> yInput = new Input<List<Number>>("y","y-axis of data, must be specified", new ArrayList<Number>(), Validate.REQUIRED);
	public Input<List<Object>> xInput = new Input<List<Object>>("x","x-axis of data, if not specified, takes values [0,... sizeof(y)]", new ArrayList<Object>());
	public Input<List<Double>> erroBarsInput = new Input<List<Double>>("error","represent errors in y, ", new ArrayList<Double>());
	public Input<String> yAxisInput = new Input<String>("yAxis","label of the y-axis", "Y");
	public Input<String> xAxisInput = new Input<String>("xAxis","label of the x-axis", "X");
	public Input<String> seriesNameInput = new Input<String>("seriesName","name of the series");
	// x can be a Number like int or double but can also be Date

	enum LineStyle {
		  none,
		  solid,
		  dash_dot,
		  dash_dash,
		  dot_dot;

	}
	
	public Input<LineStyle> lineStyleInput = new Input<LineStyle>("linestyle", "stroke used for drawing series line, one of " + Arrays.toString(LineStyle.values()), LineStyle.solid, LineStyle.values());
	public Input<BasicStroke> lineStyleInput2 = new Input<BasicStroke>("ls", "stroke used for drawing series line -- overrides basicStroke");
	public Input<Color> LineColorInput = new Input<Color>("lineColor", "colour used for drawing series line");
	public Input<Color> LineColorInput2 = new Input<Color>("lc", "colour used for drawing series line -- synonym with lineColor");
	public Input<SeriesMarker> MarkerInput = new Input<SeriesMarker>("marker","one of " + Arrays.toString(SeriesMarker.values()), SeriesMarker.DIAMOND, SeriesMarker.values());
//	public Input<SeriesMarker> MarkerInput2 = new Input<SeriesMarker>("m","one of " + Arrays.toString(SeriesMarker.values()), null, SeriesMarker.values());

	public Input<Color> MarkerColorInput = new Input<Color>("markerColor","colour used for drawing marker");
	public Input<Color> MarkerColorInput2 = new Input<Color>("mc","colour used for drawing marker -- synonym with markerColor");

	
	@Override
	public void initAndValidate() {
	}

	public Color getLineColor() {
		if (LineColorInput2.get()!=null) {
			return LineColorInput2.get();
		}
		return LineColorInput.get();
	}

	public BasicStroke getlineStyle() {
		if (lineStyleInput2.get() != null) {
			return lineStyleInput2.get();
		}
		switch (lineStyleInput.get()) {
		case none:return SeriesLineStyle.NONE.getBasicStroke();
		case solid:return SeriesLineStyle.SOLID.getBasicStroke();
		case dash_dot:return SeriesLineStyle.DASH_DOT.getBasicStroke();
		case dash_dash:return SeriesLineStyle.DASH_DASH.getBasicStroke();
		case dot_dot:return SeriesLineStyle.DOT_DOT.getBasicStroke();
		}
		return SeriesLineStyle.SOLID.getBasicStroke();
	}

	public Color getMarkerColor() {
		if (MarkerColorInput2.get() != null) {
			return MarkerColorInput2.get();
		}
		return MarkerColorInput.get();
	}

	public SeriesMarker getMarker() {
		return MarkerInput.get();
	}
}


