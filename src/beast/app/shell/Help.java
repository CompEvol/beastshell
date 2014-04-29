package beast.app.shell;



import beast.app.DocMaker;
import beast.core.BEASTObject;
import bsh.ClassIdentifier;

public class Help {
	/**
	 *  used for displaying help, if provided 
	 */
	static BEASTStudio studio = null;

    /** 
help(beast.util.TreeParser);

     * generates HTML pages *
     */
    static DocMaker m_docMaker = new DocMaker();
	
	
	static public void help() {
		showHelp("<html>" +
"<h1>Documentation</h1>\n" +
"\n" +
"<h2>Description:</h2>\n" +
"\n" +
"     ‘help’ is the primary interface to the help systems.\n" +
"\n" +
"<h2>Usage:</h2>\n" +
"\n" +
"     help(command)\n" +
"     help(class)\n" +
"     \n" +
"<h2>Arguments:</h2>\n" +
"\n" +
"    command: show help for a command\n" +
"\n" +
"    class: show help for class if it is a BEASTObject\n" +
"\n" +
"</html>");
	}

	static public void help(Object o) {
		if (o instanceof String) {
			try {
				// check if it is a class
				 o = Class.forName((String)o).newInstance();
			} catch (Exception e) {
				// TODO: check if it is a command
				e.printStackTrace();
			}
		} else if (o instanceof ClassIdentifier) {
			try {
				Class clazz = ((ClassIdentifier)o).getTargetClass(); 
				o = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (o instanceof BEASTObject) {
			try {
				String help = m_docMaker.getHTML(o.getClass().getName(), true);
				showHelp(help);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			studio.rightLowerPaneTab.setSelectedIndex(1);
			studio.classBrowser.setClist(o.getClass().getPackage().getName());
			studio.classBrowser.setMlist(o.getClass().getSimpleName());
			//showHelp("No help available for " + o);
		}

	}

	static private void showHelp(String string) {
		if (studio == null) {
			try {
				string = string.replaceAll("<[^>]*>", "");
				System.out.println(string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			studio.helpPane.setText(string);
			studio.rightLowerPaneTab.setSelectedIndex(0);
		}
	}


	public static void main(String[] args) {
		Help.help();
	}
}
