package beast.app.shell;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import beast.app.util.Utils;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
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
		File file = Utils.getSaveFile("Save chart as", new File("."), "Image file (*.pdf, *.png, *.jpg, *.bmp)", "pdf", "png", "jpg", "bmp");
		if (file != null) {
			JPanel m_Panel = panel;

				String sFileName = file.getAbsolutePath();
				if (sFileName != null && !sFileName.equals("")) {
                    if (sFileName.toLowerCase().endsWith(".pdf")) {
                    	try {
                    	com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
                    	PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(sFileName));
                    	doc.setPageSize(new com.itextpdf.text.Rectangle(m_Panel.getWidth(), m_Panel.getHeight()));
                    	doc.open();
                    	PdfContentByte cb = writer.getDirectContent();
                    	Graphics2D g = new PdfGraphics2D(cb, m_Panel.getWidth(), m_Panel.getHeight());
                    	 
						BufferedImage bi;
						bi = new BufferedImage(m_Panel.getWidth(), m_Panel.getHeight(), BufferedImage.TYPE_INT_RGB);
						//g = bi.getGraphics();
						g.setPaintMode();
						g.setColor(getBackground());
						g.fillRect(0, 0, m_Panel.getWidth(), m_Panel.getHeight());
						m_Panel.paint(g);
						//m_Panel.printAll(g);

                    	g.dispose();
                    	doc.close();
                    	} catch (Exception e) {
							JOptionPane.showMessageDialog(m_Panel, "Export may have failed: " + e.getMessage());
						}
                        repaint();
                    	return;
                    } else 	if (sFileName.toLowerCase().endsWith(".png") || sFileName.toLowerCase().endsWith(".jpg")
							|| sFileName.toLowerCase().endsWith(".bmp")) {
						BufferedImage bi;
						Graphics g;
						bi = new BufferedImage(m_Panel.getWidth(), m_Panel.getHeight(), BufferedImage.TYPE_INT_RGB);
						g = bi.getGraphics();
						g.setPaintMode();
						g.setColor(getBackground());
						g.fillRect(0, 0, m_Panel.getWidth(), m_Panel.getHeight());
						m_Panel.printAll(g);
						try {
							if (sFileName.toLowerCase().endsWith(".png")) {
								ImageIO.write(bi, "png", new File(sFileName));
							} else if (sFileName.toLowerCase().endsWith(".jpg")) {
								ImageIO.write(bi, "jpg", new File(sFileName));
							} else if (sFileName.toLowerCase().endsWith(".bmp")) {
								ImageIO.write(bi, "bmp", new File(sFileName));
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null,
									sFileName + " was not written properly: " + e.getMessage());
							e.printStackTrace();
						}
						return;
					}
					JOptionPane.showMessageDialog(null, "Extention of file " + sFileName
							+ " not recognized as png,bmp,jpg or pdf file");
				}
			}
			
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
