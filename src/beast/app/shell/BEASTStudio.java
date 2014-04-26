package beast.app.shell;

import jam.framework.DocumentFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import beast.app.draw.ModelBuilder;
import beast.app.util.Utils;
import bsh.ClassPathException;
import bsh.Interpreter;
import bsh.UtilEvalError;
import bsh.Variable;
import bsh.util.ClassBrowser;
import bsh.util.JConsole;

public class BEASTStudio extends JSplitPane {
	public final static String ICONPATH = "beastapp/shell/icons/";
	public final static String VERSION = "0.0.1";
	
	private static final long serialVersionUID = 1L;

	JSplitPane splitpaneleft;
	JSplitPane splitpaneright;

	JTabbedPane helpPaneTab;
	JTabbedPane rightUpperPaneTab;
	JTextPane variablesPane;
	HistoryPanel historyPane;
	
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
		variablesPane = new JTextPane();
		
		rightUpperPaneTab = new JTabbedPane();
		rightUpperPaneTab.addTab("Variables", variablesPane);
		
		historyPane = new HistoryPanel(this);
		rightUpperPaneTab.addTab("History", historyPane);
		
		
		JScrollPane historyScrollPane = new JScrollPane(rightUpperPaneTab); 
		splitpaneright.add(historyScrollPane);
		
		plotPane = new JPanel();
		JScrollPane plotScrollPane = new JScrollPane(plotPane); 
		splitpaneright.add(plotScrollPane);
		
		
		add(splitpaneleft);
		add(splitpaneright);
		console.historyPanel = historyPane;

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
		variablesPane.setText(str);
		
	}
	
	void doAbout() {
		JOptionPane.showMessageDialog(frame, "BEAST shell version " + VERSION);
	}
	
	void doQuit() {
        if (!quit()) {
            return;
        }
        historyPane.saveBackup();
        frame.dispose();
        //intances--;
        //if (intances == 0) {
            System.exit(0);
        //}
	}
	
	JFrame frame;
	
	public static void main(String[] args) {
		bsh.util.Util.startSplashScreen();
		
		JFrame frame = new JFrame();
		final BEASTStudio studio = new BEASTStudio(args);
		studio.setup();
		studio.frame = frame;
		
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
            	studio.doQuit();
            }
        });

        if (Utils.isMac()) {
            // set up application about-menu for Mac
            // Mac-only stuff
            try {
                URL url = ClassLoader.getSystemResource(BEASTStudio.ICONPATH + "beauti.png");
                Icon icon = null;
                if (url != null) {
                    icon = new ImageIcon(url);
                } else {
                    System.err.println("Unable to find image: " + BEASTStudio.ICONPATH + "beauti.png");
                }
                jam.framework.Application application = new jam.framework.MultiDocApplication(null, "BEAUti", "about", icon) {

                    @Override
                    protected JFrame getDefaultFrame() {
                        return null;
                    }

                    @Override
                    public void doQuit() {
                        studio.doQuit();
                    }

                    @Override
                    public void doAbout() {
                       studio.doAbout();
                    }

                    @Override
                    public DocumentFrame doOpenFile(File file) {
                        return null;
                    }

                    @Override
                    public DocumentFrame doNew() {
                        return null;
                    }
                };
                jam.mac.Utils.macOSXRegistration(application);
            } catch (Exception e) {
                // ignore
            }
            try {
                Class<?> class_ = Class.forName("jam.maconly.OSXAdapter");
                Method method = class_.getMethod("enablePrefs", boolean.class);
                method.invoke(null, false);
            } catch (java.lang.Exception e) {
                // ignore
            }
        }
		
		
		studio.interpreter = new Interpreter( studio.console );
		studio.interpreter.studio = studio;
		bsh.util.Util.endSplashScreen();
		studio.interpreter.run();
	}


} // RBEASTStudio
