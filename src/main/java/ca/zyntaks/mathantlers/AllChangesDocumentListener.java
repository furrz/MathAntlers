package ca.zyntaks.mathantlers;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class AllChangesDocumentListener implements DocumentListener {
    private final Runnable updated;

    public AllChangesDocumentListener(Runnable updated) {
        this.updated = updated;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updated.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updated.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updated.run();
    }
}
