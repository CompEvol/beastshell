package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import beast.app.DocMaker;
import beast.util.AddOnManager;

public class HelpBrowser extends JPanel implements HyperlinkListener {
    private static final long serialVersionUID = 1L;
    final static String INITIAL_PAGE = "doc/html/index.html";
    final static String BEAST_DOC_DIR = AddOnManager.getPackageUserDir() + "/beastshell/doc/BeastObjects/";
    
    
    /**
     * generates HTML pages *
     */
    static DocMaker docMaker;
    static boolean docsGenerated = false;
    /**
     * browser stack *
     */
    List<Object> pages = new ArrayList<Object>();
    
    int currentPage = 0;

    JEditorPane editorPane;
    JButton btnPrev;
    JButton btnNext;
    JButton btnHome;


    public HelpBrowser() {
    	
    	setLayout(new BorderLayout());
        if (docMaker == null) {
        	if (!new File(BEAST_DOC_DIR).exists()) {
        		new File(BEAST_DOC_DIR).mkdirs();
        	}
            docMaker = new DocMaker(new String[]{BEAST_DOC_DIR});
        }

        // initialise JEditorPane
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));

        editorPane.addHyperlinkListener(this);

        JScrollPane scroller = new JScrollPane(editorPane);

        // add the navigation buttons at the top
        JToolBar toolBar = new JToolBar();
        
		btnPrev = new JButton("");
		btnPrev.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/prev.png")));
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goPrev();
			}

		});
		btnPrev.setToolTipText("Show previous help page");
		toolBar.add(btnPrev);
		
		btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goNext();
			}
		});
		btnNext.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/next.png")));
		btnNext.setToolTipText("Show next help page");
		toolBar.add(btnNext);

		btnHome = new JButton("");
		btnHome.setIcon(new ImageIcon(EditorPanel.class
				.getResource("/beast/app/shell/icons/home.png")));
		btnHome.setToolTipText("Goto home of help");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goHome();
			}
		});
		toolBar.add(btnHome);
//		m_backwardButton.setMnemonic(KeyEvent.VK_RIGHT);
//		m_forwardButton.setMnemonic(KeyEvent.VK_LEFT);

		add(toolBar, BorderLayout.NORTH);
        add(scroller, BorderLayout.CENTER);

        try {
        	String path = new File(".").getAbsolutePath();
        	if (new File(path  + "/" + INITIAL_PAGE).exists()) {
            	setURL(new URL("file://" + path  + "/" + INITIAL_PAGE));
        	} else {
            	setURL(new URL("file://" + AddOnManager.getPackageUserDir() + "/beastshell/" + INITIAL_PAGE));
        	}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        
    } // c'tor
    
    public void setURL(URL url) {
    	while (pages.size() > currentPage + 1) {
        	pages.remove(pages.size() - 1);
    	}
    	pages.add(url);
    	currentPage = pages.size() - 1;
        updateState();
    }

    public void setText(String sHTML) {
    	while (pages.size() > currentPage + 1) {
        	pages.remove(pages.size() - 1);
    	}
    	pages.add(sHTML);
    	currentPage = pages.size() - 1;
        updateState();
    }

    protected void goHome() {
    	currentPage = 0;
        updateState();		
	}

	private void goPrev() {
        if (currentPage > 0) {
            currentPage--;
        }
        updateState();
	}

	protected void goNext() {
        if (currentPage < pages.size() - 1) {
            currentPage++;
        }
        updateState();
	}

    @Override
    public void hyperlinkUpdate(HyperlinkEvent link) {
//        try {
            HyperlinkEvent.EventType type = link.getEventType();
            if (type == HyperlinkEvent.EventType.ACTIVATED) {
            	// check whether it is help on a BEASTObject
                String beastObject = link.getDescription();
                if (beastObject.equals("BEASTObjectDocs")) {
                    // magic initialisation of DocHelper
                	try {
                		if (!docsGenerated) {
                			docMaker.generateDocs();
                			docsGenerated = true;
                		}
						setURL(new URL("file://" + BEAST_DOC_DIR + "/contents.html"));
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                beastObject = beastObject.replaceAll(".html", "");
				Class c = null;
                try {
                	c = Class.forName(beastObject);
				} catch (ClassNotFoundException e1) {
					c = null;
				}
                if (c != null) {
                    try {
                    	setText(docMaker.getHTML(beastObject, false));
                    } catch (Exception e) {
                    }
                } else {
                	
                	// check whether it is a regular page on the file system
                	String docPage = link.getDescription();
                	if (new File(docPage).exists()) {
                    	String path = new File(".").getAbsolutePath();
                       	try {
							setURL(new URL("file://" + path  + "/" + docPage));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
                       	return;
                	} else if(new File(AddOnManager.getPackageUserDir() + "/beastshell/" + docPage).exists()) {
                    	try {
							setURL(new URL("file://" + AddOnManager.getPackageUserDir() + "/beastshell/" + docPage));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
                       	return;
                	}
                	
                	// check whether it is a BeastShell command
                	// TODO
                	
                	// give up, it is probably an external link then
					setURL(link.getURL());
                	
                }

                	
            }
//        } catch (Exception e) {
            // ignore
//            System.err.println(e.getMessage());
//        }
    } // hyperlinkUpdate

    /**
     * change html text and enable/disable buttons (where appropriate) *
     */
    void updateState() {
        Object page = pages.get(currentPage);
        if (page instanceof String) {
        	editorPane.setText((String) page);
        } else if (page instanceof URL) {
	        try {
	        	editorPane.setPage((URL) page);
	        } catch (IOException e) {
	        	editorPane.setText(e.getMessage());
	        }
        }
        btnPrev.setEnabled(currentPage > 0);
        btnNext.setEnabled(currentPage < pages.size() - 1);
    } // updateState


}
