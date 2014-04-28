package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.XChartPanel;

public class ChartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	List<Chart> charts;
	int current = -1;
	JButton btnNextChart;
	JButton btnPrevChart;
	JButton btnExport;
	JButton btnClear;
	JPanel panel;
	
	public ChartPanel() {
		charts = new ArrayList<Chart>();
		
		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		btnPrevChart = new JButton("");
		btnPrevChart.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/prev.png")));
		btnPrevChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPrev();
			}
		});
		btnPrevChart.setToolTipText("Show previous chart");
		toolBar.add(btnPrevChart);
		
		btnNextChart = new JButton("");
		btnNextChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doNext();
			}
		});
		btnNextChart.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/next.png")));
		btnNextChart.setToolTipText("Show next chart");
		toolBar.add(btnNextChart);

		btnClear = new JButton("");
		btnClear.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/clear.png")));
		btnClear.setToolTipText("Clear all charts");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doClear();
			}
		});
		toolBar.add(btnClear);
		
		btnExport = new JButton("");
		btnExport.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/export.png")));
		btnExport.setToolTipText("Export current chart to bitmap or pdf");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doExport();
			}
		});
		toolBar.add(btnExport);
		panel = new JPanel(); 
		add(panel, BorderLayout.CENTER);
		update();
	}

	protected void doExport() {
	}

	protected void doClear() {
		current = -1;
		charts.clear();
		update();
	}

	protected void doPrev() {
		current--;
		update();
	}

	protected void doNext() {
		current++;
		update();
	}

	void update() {
		btnPrevChart.setEnabled(current >= 1);
		btnNextChart.setEnabled(current >= 0 && current < charts.size() - 1);
		btnExport.setEnabled(current > -1);
		btnClear.setEnabled(charts.size() > 0);
		panel.removeAll();
		if (current >= 0) {
			panel.add(new XChartPanel(charts.get(current)));
		}
		panel.repaint();
	}
	
	public void addChart(Chart chart) {
		charts.add(chart);
		current = charts.size() - 1;
		update();
	}
}
