package beast.app.shell;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import beast.app.DocMaker;
import beast.core.BEASTObject;
import beast.util.AddOnManager;
import bsh.ClassIdentifier;
import bsh.Interpreter;

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
				 return;
			} catch (Exception e) {
				// TODO: check if it is a command
				e.printStackTrace();
            	for (String path : new String[]{"../beast/commands/", "../bsh/commands/"}) {
            		String scriptPath = path +"/"+ (String) o +".bsh";
            		InputStream in = Interpreter.class.getResourceAsStream( scriptPath );
            		if (in != null) {
            			StringBuffer buf = new StringBuffer();
            			try {
            				char ch = ' ';
            				while (ch != '\n') {
            					ch = (char) in.read();
            					buf.append(ch);
            				}
            				String header = buf.toString();
            				if (header.indexOf("@see(")>=0) {
            					header = header.substring(header.indexOf("@see(") + 5, header.indexOf(")"));
                            	if (canLoadPage(header)) {
                        			studio.rightLowerPaneTab.setSelectedIndex(0);
                            		return;
                            	}
            				}
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
            		}
                }
            	// fall back
            	String page = "doc/html/beanshell/bshcommands.html#" + (String) o;
            	if (canLoadPage(page)) {
        			studio.rightLowerPaneTab.setSelectedIndex(0);
            		return;
            	}

			
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
			// show the class browser
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


    static boolean canLoadPage(String docPage) {
    	String page = docPage;
    	if (page.contains("#")) {
    		page = page.substring(0, page.indexOf('#'));
    	}
    	if (new File(page).exists()) {
        	String path = new File(".").getAbsolutePath();
           	try {
           		studio.helpPane.setURL(new URL("file://" + path  + "/" + docPage));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
           	return true;
    	} else if(new File(AddOnManager.getPackageUserDir() + "/beastshell/" + page).exists()) {
        	try {
        		studio.helpPane.setURL(new URL("file://" + AddOnManager.getPackageUserDir() + "/beastshell/" + docPage));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
           	return true;
    	}
    	return false;
	}
	
	
	public static void main(String[] args) {
		Help.help();
	}
}
