package ca.zyntaks.mathantlers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.prefs.Preferences;

public class MainWindow extends JFrame {

    private final JMenu fileMenu;
    private final JTextArea textArea;
    private final JLabel drawingArea;

    public MainWindow() {
        super("MathAntlers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        try {
            Preferences root = Preferences.userRoot()
                    .node("ca.zyntaks.mathantlers")
                    .node("ca.zyntaks.mathantlers.MainWindow");

            setSize(root.getInt("width", getPreferredSize().width),
                    root.getInt("height", getPreferredSize().height));

            if (root.getBoolean("maximized", false))
                setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Preferences.userRoot().putInt("width", getWidth());
                    Preferences.userRoot().putInt("height", getHeight());
                    Preferences.userRoot().putBoolean("maximized",
                            (getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH);
                }
            });
        } catch (SecurityException e) {
            System.err.println("Cannot save user window size preference.");
        }

        setLocationByPlatform(true);


        JMenuBar menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        EmptyBorder padding = new EmptyBorder(16, 16, 16, 16);

        textArea = new JTextArea(1, 10);
        textArea.setBorder(padding);

        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBorder(null);

        drawingArea = new JLabel();
        drawingArea.setBorder(padding);

        JScrollPane drawingAreaScrollPane = new JScrollPane(drawingArea);
        drawingAreaScrollPane.getViewport().setBackground(null);
        drawingAreaScrollPane.setBackground(null);
        drawingAreaScrollPane.setBorder(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textAreaScrollPane, drawingAreaScrollPane);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.4);
        splitPane.setDividerLocation(0.4);
        splitPane.setBorder(null);
        add(splitPane);
    }

    public MainWindow addMenuItem(String name, Consumer<MainWindow> action) {
        fileMenu.add(name).addActionListener(e -> action.accept(this));
        return this;
    }

    public MainWindow onEdit(BiConsumer<MainWindow, String> handler) {
        textArea.getDocument().addDocumentListener(
                new AllChangesDocumentListener(() -> handler.accept(this, textArea.getText())));
        return this;
    }


    public void showError(String heading, String error) {
        drawingArea.setForeground(Color.RED);
        drawingArea.setIcon(null);
        drawingArea.setText("<html><b>" + heading + "</b><br/>" +
                error
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
                        .replaceAll("\n", "<br/>"));
    }

    public void showIcon(Icon icon) {
        drawingArea.setText(null);
        drawingArea.setIcon(icon);
        drawingArea.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        drawingArea.setForeground(Color.BLACK);
    }
}
