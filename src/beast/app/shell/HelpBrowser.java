package beast.app.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class HelpBrowser extends JPanel implements HyperlinkListener {
    private static final long serialVersionUID = 1L;
    /**
     * generates HTML pages *
     */
    static DocMaker docMaker;
    /**
     * browser stack *
     */
    List<String> pages = new ArrayList<String>();
    int currentPage = 0;

    JEditorPane editorPane;
    JButton btnPrev;
    JButton btnNext;
    JButton btnHome;


    public HelpBrowser() {
    	setLayout(new BorderLayout());
        if (docMaker == null) {
            docMaker = new DocMaker();
        }

        // initialise JEditorPane
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
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

    } // c'tor
    
    public void setText(String sHTML) {
    	pages.add(sHTML);
    	currentPage = pages.size() - 1;
    	editorPane.setText(sHTML);
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
        try {
            HyperlinkEvent.EventType type = link.getEventType();
            if (type == HyperlinkEvent.EventType.ACTIVATED) {
                String sPlugin = link.getDescription();
                sPlugin = sPlugin.replaceAll(".html", "");
                // update browser stack
                currentPage++;
                while (currentPage < pages.size()) {
                    pages.remove(currentPage);
                }
                try {
                    String sHTML = docMaker.getHTML(sPlugin, false);
                    pages.add(sHTML);
                } catch (Exception e) {
                    // ignore
                    System.err.println("HelpBrowser: Something is wrong: " + e.getClass().getName() + " " + e.getMessage());
                }                
                updateState();
            }
        } catch (Exception e) {
            // ignore
            System.err.println(e.getMessage());
        }
    } // hyperlinkUpdate

    /**
     * change html text and enable/disable buttons (where appropriate) *
     */
    void updateState() {
        String sHTML = pages.get(currentPage);
        editorPane.setText(sHTML);
        btnPrev.setEnabled(currentPage > 0);
        btnNext.setEnabled(currentPage < pages.size() - 1);
    } // updateState

}
