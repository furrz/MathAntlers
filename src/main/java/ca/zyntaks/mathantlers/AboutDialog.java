package ca.zyntaks.mathantlers;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent) {
        super(parent, "About MathAntlers", true);

        try {
            JEditorPane aboutLabel = new JEditorPane(Main.class.getResource("about.html"));
            aboutLabel.setEditable(false);
            aboutLabel.addHyperlinkListener(hle -> {
                if (hle.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    try {
                        Desktop.getDesktop().browse(hle.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }
            });
            add(aboutLabel);

            pack();
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(parent,
                    "An error occurred trying to display the About pane.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
