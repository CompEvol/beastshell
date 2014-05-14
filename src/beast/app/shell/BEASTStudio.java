package beast.app.shell;


import jam.framework.DocumentFrame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import beast.app.BEASTVersion;
import beast.app.beauti.BeautiPanel;
import beast.app.draw.MyAction;
import beast.app.util.Utils;
import bsh.BshClassManager;
import bsh.ClassPathException;
import bsh.NameSource;
import bsh.Variable;
import bsh.classpath.ClassManagerImpl;
import bsh.util.ClassBrowser;
import bsh.util.JConsole;
import bsh.util.NameCompletionTable;

public class BEASTStudio extends JSplitPane {
	public final static String ICONPATH = "beastapp/shell/icons/";
	public final static String PACKAGENAME = "/BEASTShell/";
	public final static String VERSION = "0.0.1";
	
	private static final long serialVersionUID = 1L;

	JFrame frame;
	JSplitPane splitpaneleft;
	JSplitPane splitpaneright;

	//JTabbedPane helpPaneTab;
	JTabbedPane rightUpperPaneTab;
	JTabbedPane rightLowerPaneTab;
	VariablesPanel variablesPane;
	HistoryPanel historyPane;
	public EditorPanel editorPanel;
	
	public ChartPanel plotPane;
	JConsole console;
	HelpBrowser helpPane;
	ClassBrowser classBrowser;
	public Interpreter interpreter;

	public BEASTStudio(String[] args) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		// TODO process arguments
	}

	private void setup() {
		splitpaneleft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		editorPanel = new EditorPanel();
		splitpaneleft.add(editorPanel);
		
		console = new JConsole(this);
		splitpaneleft.add(console);
	

		splitpaneright = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		variablesPane = new VariablesPanel();
		
		rightUpperPaneTab = new JTabbedPane();
		rightUpperPaneTab.addTab("Variables", variablesPane);
		
		historyPane = new HistoryPanel(this);
		rightUpperPaneTab.addTab("History", historyPane);
		
		splitpaneright.add(rightUpperPaneTab);
		
		rightLowerPaneTab = new JTabbedPane();
		
		helpPane = new HelpBrowser();
		rightLowerPaneTab.addTab("Help", helpPane);

		classBrowser = new ClassBrowser();
		//TODO: let the splitter pane determine size
		classBrowser.setMaximumSize(new Dimension(512,512));
		classBrowser.setSize(new Dimension(512,512));

		try {
			classBrowser.init();
		} catch (ClassPathException e) {
			e.printStackTrace();
		}
		//rightLowerPaneTab.addTab("Class Browser", classBrowser);

		plotPane = new ChartPanel(this);
		JScrollPane plotScrollPane = new JScrollPane(plotPane);
		rightLowerPaneTab.addTab("Plots", plotScrollPane);
		
		
		
		splitpaneright.add(rightLowerPaneTab);

		
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
		variablesPane.update(vars);
	}
	
	void doAbout() {
		JOptionPane.showMessageDialog(frame, "BEAST shell version " + VERSION);
	}
	
	void doQuit() {
        if (!quit()) {
            return;
        }
        historyPane.saveBackup();
        editorPanel.saveStatus();
        frame.dispose();
        //intances--;
        //if (intances == 0) {
            System.exit(0);
        //}
	}
	
	private void setNameCompletion() {
//		if (true) {
//			// TODO: enable auto completion
//			return;
//		}
        // Access to read classpath is protected 
        try {
	        NameCompletionTable nct = new NameCompletionTable();
	        nct.add( interpreter.getNameSpace() );
	        try {
	        	BshClassManager bcm = interpreter.getNameSpace().getClassManager();
	            if (bcm != null ) {
	            	 NameSource classNamesSource = ((ClassManagerImpl) bcm).getClassPath();
	                 nct.add( classNamesSource );
	            }
	        } catch ( ClassPathException e ) {
	                throw new RuntimeException("classpath exception in name compl:"+e);
	        }
	        console.setNameCompletion( nct );
        // end setup name completion
        } catch ( SecurityException e ) { }
	}
	
	
	MyAction a_quit = new MyAction("Exit", "Exit Program", "exit", KeyEvent.VK_F4) {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent ae) {
            // if (!m_doc.m_bIsSaved) {
            if (!quit()) {
                return;
            }
            System.exit(0);
        }
    }; // class ActionQuit

    MyAction a_about = new MyAction("About", "Help about", "about", -1) {
        private static final long serialVersionUID = -1;

        public void actionPerformed(ActionEvent ae) {
        	Help.help("doc/html/about.html");
        }
    }; // class ActionAbout

    MyAction a_help = new MyAction("Help", "Help on current panel", "help", -1) {
        private static final long serialVersionUID = -1;

        public void actionPerformed(ActionEvent ae) {
        	Help.help("doc/html/index.html");
        }
    }; // class ActionHelp
	
	private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menuBar.add(fileMenu);
        //fileMenu.add(a_new);
        //fileMenu.add(a_load);
        fileMenu.addSeparator();
        if (!Utils.isMac()) {
            fileMenu.addSeparator();
            fileMenu.add(a_quit);
        }

        JMenu modeMenu = new JMenu("Mode");
        menuBar.add(modeMenu);
        modeMenu.setMnemonic('M');


        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        viewMenu.setMnemonic('V');

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        helpMenu.setMnemonic('H');
        helpMenu.add(a_help);
        //helpMenu.add(a_citation);
        if (!Utils.isMac()) {
            helpMenu.add(a_about);
        }

        return menuBar;
	}
	
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
		frame.setSize(new Dimension(2024, 1024));
		frame.setVisible(true);
		studio.setDividerLocation(0.5);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	studio.doQuit();
            }
        });

		frame.setJMenuBar(studio.createMenuBar());
		
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
        } else {
        	try {
        	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        	        if ("Nimbus".equals(info.getName())) {
        	            UIManager.setLookAndFeel(info.getClassName());
        	            break;
        	        }
        	    }
        	} catch (Exception e) {
        	    // If Nimbus is not available, you can set the GUI to another look and feel.
        	}
        }
		
		
		studio.interpreter = new Interpreter(studio.console, studio);
		studio.interpreter.studio = studio;
		studio.setNameCompletion();
		studio.splitpaneleft.setDividerLocation(0.3);
		studio.splitpaneright.setDividerLocation(0.5);
		studio.setDividerLocation(0.7);
		bsh.util.Util.endSplashScreen();
//		new Thread() {
//			public void run() {
//				try {sleep(15000);}catch (Exception e){}
//				studio.setDividerLocation(0.7);
//			};
//		}.run();
		studio.interpreter.run();
	}

} // RBEASTStudio
