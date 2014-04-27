package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import beast.app.beauti.BeautiDoc;
import beast.app.util.Utils;

public class EditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	List<String> fileNames;

	public EditorPanel() {
		fileNames = new ArrayList<String>();
		setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doNew();
			}

		});
		btnNewButton.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/new.png")));
		btnNewButton.setToolTipText("Start new editor");
		toolBar.add(btnNewButton);

		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/open.png")));
		btnNewButton_3.setToolTipText("Open file");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doOpen();
			}
		});
		toolBar.add(btnNewButton_3);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setSelectedIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/recent.png")));
		btnNewButton_1.setToolTipText("Recently opened files");
		toolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/save.png")));
		btnNewButton_2.setToolTipText("Save current editor");
		toolBar.add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSaveAll();
			}
		});
		btnNewButton_4.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/saveall.png")));
		btnNewButton_4.setToolTipText("Save all files");
		toolBar.add(btnNewButton_4);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
	}

	private void doNew() {
		RSyntaxTextArea /* JTextArea */textPane;
		textPane = new RSyntaxTextArea();
		textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textPane.setCodeFoldingEnabled(true);
		textPane.setAntiAliasingEnabled(true);
		addTab("New file", textPane);
		fileNames.add(null);
	}

	private void doOpen() {
		File file = Utils.getLoadFile("Open BEASTscript file");
		if (file != null) {
			String text = null;
			try {
				text = BeautiDoc.load(file);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Could not open file: " + e.getMessage());
				return;
			}
			RSyntaxTextArea /* JTextArea */textPane;
			textPane = new RSyntaxTextArea();
			textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
			textPane.setCodeFoldingEnabled(true);
			textPane.setAntiAliasingEnabled(true);
			textPane.setText(text);
			addTab(file.getName(), textPane);
			fileNames.add(file.getAbsolutePath());
		}
	}

	private void doSave() {
		int i = tabbedPane.getSelectedIndex();
		doSave(i);
	}

	private void doSaveAll() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			doSave(i);
		}
	}

	private void doSave(int i) {
		File file = null;
		if (fileNames.get(i) == null) {
			tabbedPane.setSelectedIndex(i);
			file = Utils.getSaveFile("Open BEASTscript file");
		} else {
			file = new File(fileNames.get(i));
		}
		if (file != null) {
			RSyntaxTextArea textPane = (RSyntaxTextArea) tabbedPane
					.getSelectedComponent();
			String text = textPane.getText();
			try {
				FileWriter outfile = new FileWriter(file);
				outfile.write(text);
				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						this,
						"Something went wrong writing the file + "
								+ file.getName() + ": " + e.getMessage());
				return;
			}
		}
	}

	void addTab(String title, final JComponent panel) {
		tabbedPane.addTab(title, panel);
		int i = tabbedPane.indexOfComponent(panel);
		tabbedPane.setTabComponentAt(i, new ButtonTabComponent(this));
	}
}
