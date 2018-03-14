package beast.app.shell;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;

import beast.app.DocMaker;
import beast.core.BEASTObject;
import beast.util.PackageManager;
import bsh.ClassIdentifier;
import bsh.EvalError;
import bsh.UtilEvalError;

public class Help {
	/**
	 *  used for displaying help, if provided 
	 */
	static BEASTStudio studio = null;

    /** 
help(beast.util.TreeParser);

     * generates HTML pages *
     */
    static DocMaker m_docMaker = HelpBrowser.docMaker;
	
	
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
"     help(command)<br>\n" +
"     help(class)\n" +
"     \n" +
"<h2>Arguments:</h2>\n" +
"\n" +
"    command: show help for a command\n" +
"\n" +
"    class: show help for class if it is a BEASTObject<br>\n" +
"    otherwise show the class browser.\n" +
"\n" +
"</html>");
	}

	static public void help(Object o) {
		if (o instanceof String) {
			try {
				Object target = studio.interpreter.get((String)o);
				if (target != null) {
					help(target);
					return;
				}
			} catch (EvalError e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
			try {
				// check if it is a class
				 o = Class.forName((String)o).newInstance();
				 help(o);
				 return;
			} catch (Exception e) {
				// TODO: check if it is a command
				//e.printStackTrace();
				//
            	for (String path : studio.interpreter.getNameSpace().getImportedCommands()) {
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
            		} else {
            			// it may be a Java-class command
            			path = path.replaceAll("/", ".");
            			if (path.startsWith(".")) {
            				path = path.substring(1);
            			}
           				 try {
							o = Class.forName(path + "." +(String)o).newInstance();
							Method method = o.getClass().getMethod("usage");
							String help = (String) method.invoke(o);
							studio.helpPane.setText(help);
							return;
						} catch (Exception e1) {
						}
            		}
                }
            	// is it a url then?
            	if (canLoadPage(o.toString())) {
        			studio.rightLowerPaneTab.setSelectedIndex(0);
            		return;
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
				if (m_docMaker == null) {
					m_docMaker = HelpBrowser.docMaker;
				}
				
				String help = m_docMaker.getHTML(o.getClass().getName(), false);
				showHelp(help);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String name = o.getClass().getCanonicalName();
			name = name.replaceAll("\\.", "/");
			name = "http://docs.oracle.com/javase/7/docs/api/" + name + ".html";
			try {
				URL url = new URL(name);
				showHelp(url);
				return;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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

	static private void showHelp(URL url) {
		if (studio == null) {
			try {
				System.out.println(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			studio.helpPane.setURL(url);
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
    	} else if(new File(PackageManager.getPackageUserDir() + BEASTStudio.PACKAGENAME + page).exists()) {
        	try {
        		studio.helpPane.setURL(new URL("file://" + PackageManager.getPackageUserDir() + BEASTStudio.PACKAGENAME + docPage));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
           	return true;
    	}
    	return false;
	}
	
    
    public static void javap( Object o ) 
    {
    	Class clas = null;
    	if ( o instanceof ClassIdentifier )
    		clas = studio.interpreter.getNameSpace().identifierToClass((ClassIdentifier) o);
    	else if ( o instanceof String )
    	{
    		if ( ((String)o).length() < 1 ) {
    			studio.console.error("javap: Empty class name.");
    			return;
    		}
        	try {
				if (studio.interpreter.get((String)o) != null) {
					o = studio.interpreter.get((String)o);
			    	if ( o instanceof ClassIdentifier ) {
			    		clas = studio.interpreter.getNameSpace().identifierToClass((ClassIdentifier) o);
					} else {
						clas = o.getClass();
					}
				} else {
					clas = studio.interpreter.getNameSpace().getClass((String)o);
				}
			} catch (EvalError | UtilEvalError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else if ( o instanceof Class )
    		clas = (Class) o;
    	else 
    		clas = o.getClass();
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append( "Class "+clas+" extends " +clas.getSuperclass() );

    	Method [] dmethods=clas.getDeclaredMethods();
    	//print("------------- Methods ----------------");
    	for(int i=0; i<dmethods.length; i++) {
    		Method m = dmethods[i];
    		if ( Modifier.isPublic( m.getModifiers() ) )
    			buf.append( m + "<br>\n");
    	}

    	//print("------------- Fields ----------------");
    	Field [] fields=clas.getDeclaredFields();
    	for(int i=0; i<fields.length; i++) {
    		Field f = fields[i];
    		if ( Modifier.isPublic( f.getModifiers() ) )
    			buf.append( f + "<br>\n");
    	}
    	showHelp(buf.toString());
    }

	
	public static void main(String[] args) {
		Help.help();
	}
}
