package beast.app.shell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import bsh.UtilEvalError;
import bsh.Variable;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class VariablesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable table;
	JScrollPane scrollPane;
	Object[][] data;
	AbstractTableModel model;
	JTextPane textPane;
	
	public VariablesPanel() {
		data = new Object[0][2];
		model = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return data[rowIndex][columnIndex];
			}
			
			@Override
			public int getRowCount() {
				return data.length;
			}
			
			@Override
			public int getColumnCount() {
				return 2;
			}
			
			@Override
			public String getColumnName(int column) {
				if (column == 0) {
					return "name";
				}
				return "value";
			}
		};
		
		table = new JTable(model);
		add(table);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		//scrollPane.setSize(200, 100);
		
		JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitter.add(scrollPane);
		textPane = new JTextPane();
		splitter.add(textPane);
		add(splitter);
		
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if (data[i][1].getClass().isArray()) {
	        		List list = new ArrayList<>();
	        		for (Object o : (Object []) data[i][1]) {
	        			list.add(o);
	        		}
	        		textPane.setText(list.toString());
				} else {
					textPane.setText(data[i][1].toString());
				}
			}
		});

	}
	
	
	public void update(Variable[] vars) {
		data = new Object[vars.length][2];
		int k = 0;
		for (Variable v : vars) {
			try {
				data[k][0] = v.getName(); 
				data[k][1] = v.getValue();//.toString();
				k++;
			} catch (UtilEvalError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.fireTableDataChanged();
	}

}
