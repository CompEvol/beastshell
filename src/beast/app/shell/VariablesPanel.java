package beast.app.shell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import bsh.UtilEvalError;
import bsh.Variable;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class VariablesPanel extends JSplitPane {
	private static final long serialVersionUID = 1L;

	private JTable table;
	JScrollPane scrollPane;
	Object[][] data;
	AbstractTableModel model;
	JTextPane textPane;
	
	public VariablesPanel() {
		super(JSplitPane.VERTICAL_SPLIT);
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
		//table.setSize(200, 200);
		
		//JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		//add(scrollPane);
		textPane = new JTextPane();
		add(new JScrollPane(textPane));
		//add(splitter);
		
		
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
	        		List list = new ArrayList();
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
		
        Arrays.sort(data, new Comparator<Object>() {
            public int compare(Object o1, Object o2)
            {	
            	String left = ((Object[])o1)[0].toString();
            	String right = ((Object[])o2)[0].toString();
            	return (left).compareToIgnoreCase(right);
            }
        });
		model.fireTableDataChanged();
	}

}
