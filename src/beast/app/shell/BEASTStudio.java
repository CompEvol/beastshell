package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import bsh.ClassPathException;
import bsh.Interpreter;
import bsh.UtilEvalError;
import bsh.Variable;
import bsh.util.ClassBrowser;
import bsh.util.JConsole;

public class BEASTStudio extends JSplitPane {
	private static final long serialVersionUID = 1L;

	JSplitPane splitpaneleft;
	JSplitPane splitpaneright;

	JTabbedPane helpPaneTab;
	JTextPane historyPane;
	JPanel plotPane;
	JConsole console;
	JTextPane helpPane;
	ClassBrowser classBrowser;
	Interpreter interpreter;

	public BEASTStudio(String[] args) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		// TODO process arguments
	}

	private void setup() {
		splitpaneleft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		helpPaneTab = new JTabbedPane();
		helpPane = new JTextPane();
		helpPane.setEditable(false);
		helpPane.setContentType("text/html");
		helpPaneTab.addTab("Help", helpPane);
		String s = "<html><h1>Help</h1>Help info goes here</html>";
		try {
			helpPane.read(new StringReader(s), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		classBrowser = new ClassBrowser();
		try {
			classBrowser.init();
		} catch (ClassPathException e) {
			e.printStackTrace();
		}
		helpPaneTab.addTab("Class Browser", classBrowser);

		JScrollPane helpScrollPane = new JScrollPane(helpPaneTab); 
		splitpaneleft.add(helpScrollPane);
		
		console = new JConsole(); 
		splitpaneleft.add(console);
		

		splitpaneright = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		historyPane = new JTextPane();
		JScrollPane historyScrollPane = new JScrollPane(historyPane); 
		splitpaneright.add(historyScrollPane);
		
		plotPane = new JPanel();
		JScrollPane plotScrollPane = new JScrollPane(plotPane); 
		splitpaneright.add(plotScrollPane);
		
		
		add(splitpaneleft);
		add(splitpaneright);

		//JSplitPane hsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//		hsplitpane.add(splitpane);
		//hsplitpane.add(splitpane2);
		
		//add(hsplitpane);
		//		//setRowHeaderView(view);
//		JScrollPane pane = new JScrollPane(cli);
//		add(pane);
	}

	protected boolean quit() {
		// TODO: do we want to save anything?
		return true;
	}

	public void update() {
		Variable [] vars = interpreter.getNameSpace().getDeclaredVariables();
		String str = "";
		for (Variable v : vars) {
			try {
				str += v.getName() + " " + v.getValue().toString() + "\n";
			} catch (UtilEvalError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		historyPane.setText(str);
		
	}
	
	public static void main(String[] args) {
		bsh.util.Util.startSplashScreen();
		
		final JFrame frame = new JFrame();
		final BEASTStudio studio = new BEASTStudio(args);
		studio.setup();
		
		Plot.studio = studio;
		Help.studio = studio;
		
		frame.setLayout(new BorderLayout());
		frame.add(studio, BorderLayout.CENTER);
		studio.setSize(1024, 768);
		frame.setSize(new Dimension(1024, 768));
		frame.setVisible(true);
		studio.setDividerLocation(0.5);
		studio.splitpaneleft.setDividerLocation(0.3);
		studio.splitpaneright.setDividerLocation(0.5);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!studio.quit()) {
                    return;
                }
                JFrame frame = (JFrame) e.getSource();
                frame.dispose();
                //intances--;
                //if (intances == 0) {
                    System.exit(0);
                //}
            }
        });

		studio.interpreter = new Interpreter( studio.console );
		studio.interpreter.studio = studio;
		bsh.util.Util.endSplashScreen();
		studio.interpreter.run();
	}


} // RBEASTStudio
