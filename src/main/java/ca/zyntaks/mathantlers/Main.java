package ca.zyntaks.mathantlers;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import ca.zyntaks.mathantlers.antlr.MathAntlersLexer;
import ca.zyntaks.mathantlers.antlr.MathAntlersParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

public class Main {
    static String sourceLaTeX = "";

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        new MainWindow()
                .addMenuItem("Copy LaTeX Code", w -> copyString(sourceLaTeX))
                .addMenuItem("Copy Rendered Math", w -> copyLaTeXImage())
                .addMenuItem("About MathAntlers", AboutDialog::new)
                .onEdit(Main::onEdit)
                .setVisible(true);
    }

    static void onEdit(MainWindow window, String text) {
        sourceLaTeX = compileToLaTeX(text);

        try {
            TeXFormula formula = new TeXFormula(sourceLaTeX);
            window.showIcon(formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 25));
        } catch (Exception e) {
            if (e.getMessage().startsWith("Problem with command align@@env at position")) {
                window.showError("You can't nest == inside of something else.",
                        "Use a single = instead.\nSorry, the LaTeX engine is pickier than I am.");
            } else {
                e.printStackTrace(System.err);

                if (e instanceof ParseException) {
                    window.showError("The LaTeX engine doesn't like your code!", e.getMessage());
                } else {
                    window.showError("Something went wrong:", e.getMessage());
                }
            }
        }
    }

    static String compileToLaTeX(String code) {
        MathAntlersLexer lexer = new MathAntlersLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathAntlersParser parser = new MathAntlersParser(tokens);
        TranslatorVisitor visitor = new TranslatorVisitor();

        ParseTree tree = parser.prog();
        return visitor.visit(tree);
    }

    static void copyString(String str) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
    }

    static void copyLaTeXImage() {
        try {
            TeXFormula formula = new TeXFormula(sourceLaTeX);
            Image image = formula.createBufferedImage(TeXConstants.STYLE_DISPLAY, 200, Color.BLACK, Color.WHITE);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableImage(image), null);
        } catch (ParseException e) {
            e.printStackTrace(System.err);
        }
    }
}

