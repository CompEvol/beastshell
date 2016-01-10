package beast.app.draw;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import beast.app.beauti.BeautiDoc;
import beast.core.BEASTInterface;
import beast.core.Input;

public class FontInputEditor extends InputEditor.Base {
	private static final long serialVersionUID = 1L;
	
	JButton button;
	
	public FontInputEditor(BeautiDoc doc) {
		super(doc);
	}

	@Override
	public Class<?> type() {
		return Font.class;
	}

	@Override
	public void init(Input<?> input, BEASTInterface plugin, int itemNr, ExpandOption bExpandOption, boolean bAddButtons) {
        m_bAddButtons = bAddButtons;
        m_input = input;
        m_beastObject = plugin;
        this.itemNr= itemNr;
        
        addInputLabel();
        
        Font font = (Font) m_input.get();
        button = new JButton("font");
        if (font != null) {
        	button.setFont(font);
        }
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editFont();
			}

		});
        add(button);
        
        add(Box.createHorizontalGlue());
        addValidationLabel();
    } // init
	
	private void editFont() {
		Font font = (Font) m_input.get();
        JFontChooser chooser = new JFontChooser();
        if (font != null) {
        	chooser.setFont(font);
        }
	
        JOptionPane optionPane = new JOptionPane(chooser,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null,
                null);
        optionPane.setBorder(new EmptyBorder(12, 12, 12, 12));

        //Frame frame = (doc != null ? doc.getFrame(): Frame.getFrames()[0]);
        final JDialog dialog = optionPane.createDialog(null, "Choose font for " + m_input.getName());
        dialog.pack();

        dialog.setVisible(true);

        int result = JOptionPane.CANCEL_OPTION;
        Integer value = (Integer) optionPane.getValue();
        if (value != null && value != -1) {
            result = value;
        }
        if (result != JOptionPane.CANCEL_OPTION) {
        	font = chooser.getFont();
        	m_input.setValue(font, m_beastObject);
        }
        button.setFont(font);
	}
}
