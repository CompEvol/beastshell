package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class HistoryPanel extends JPanel {
	private JTextField textField;
	Image image;
	
	public HistoryPanel() {
		setLayout(new BorderLayout());
		
		JTextPane textPane = new JTextPane();
		add(textPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Send selected commands to the console");
		btnNewButton.setIcon(new ImageIcon(HistoryPanel.class.getResource("/beast/app/shell/icons/toconsole.png")));
		toolBar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("remove everything from history");
		btnNewButton_1.setIcon(new ImageIcon(HistoryPanel.class.getResource("/beast/app/shell/icons/removeall.png")));
		toolBar.add(btnNewButton_1);
		
		image = Toolkit.getDefaultToolkit().getImage("/beast/app/shell/icons/clear.png");
		textField = new JTextField() {
	            protected void paintComponent(Graphics g) {  
	                super.paintComponent(g);  
	                int y = (getHeight() - image.getHeight(null))/2;  
	                g.drawImage(image, 100, y, this);  
	            }  
	        };  
		toolBar.add(textField);
		textField.setColumns(8);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("filter history by matching expression");
		btnNewButton_2.setIcon(new ImageIcon(HistoryPanel.class.getResource("/beast/app/shell/icons/clear.png")));
		toolBar.add(btnNewButton_2);
	}

}
