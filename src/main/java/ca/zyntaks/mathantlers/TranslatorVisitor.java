import org.antlr.v4.runtime.tree.ParseTree;

import java.util.stream.Collectors;

public class TranslatorVisitor extends MathAntlersBaseVisitor<String> {
    @Override
    public String visitProg(MathAntlersParser.ProgContext ctx) {
        return "\\begin{align}\n" + ctx.expr().stream()
                .map(this::visit)
                .collect(Collectors.joining("\\\\\n")) + "\n\\end{align}";
    }

    @Override
    public String visitParentheses(MathAntlersParser.ParenthesesContext ctx) {
        return " \\left( " + visit(ctx.expr()) + " \\right) ";
    }

    @Override
    public String visitBraces(MathAntlersParser.BracesContext ctx) {
        return " \\left\\{ " + visit(ctx.expr()) + " \\right\\} ";
    }

    @Override
    public String visitVar(MathAntlersParser.VarContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitNum(MathAntlersParser.NumContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitAddSub(MathAntlersParser.AddSubContext ctx) {
        return visit(ctx.expr(0)) + ctx.getToken(MathAntlersLexer.PLUSMINUS, 0).getText() + visit(ctx.expr(1));
    }

    @Override
    public String visitSquareBrackets(MathAntlersParser.SquareBracketsContext ctx) {
        return " \\left[ " + visit(ctx.expr()) + " \\right] ";
    }

    @Override
    public String visitInvisibleBrackets(MathAntlersParser.InvisibleBracketsContext ctx) {
        return "{" + visit(ctx.expr()) + "}";
    }

    @Override
    public String visitList(MathAntlersParser.ListContext ctx) {
        return visit(ctx.expr(0)) + "," + visit(ctx.expr(1));
    }

    @Override
    public String visitMulDiv(MathAntlersParser.MulDivContext ctx) {
        if (ctx.getToken(MathAntlersLexer.MULDIV, 0).getText().equals("/")) {
            return " \\frac{ " + visit(ctx.expr(0)) + " }{ " + visit(ctx.expr(1)) + " } ";
        } else {
            return visit(ctx.expr(0)) + " \\cdot " + visit(ctx.expr(1));
        }
    }

    @Override
    public String visitSummation(MathAntlersParser.SummationContext ctx) {
        return " \\sum_{ " + visit(ctx.expr(0)) + " }^{ " + visit(ctx.expr(1)) + " } ";
    }

    @Override
    public String visitPointFreeMult(MathAntlersParser.PointFreeMultContext ctx) {
        return visit(ctx.expr(0)) + visit(ctx.expr(1));
    }


    @Override
    public String visitSubscript(MathAntlersParser.SubscriptContext ctx) {
        return "{" + visit(ctx.expr(0)) + "}_{" + visit(ctx.expr(1)) + "}";
    }

    @Override
    public String visitAlignedEquality(MathAntlersParser.AlignedEqualityContext ctx) {
        return visit(ctx.expr(0)) + " & = " + visit(ctx.expr(1));
    }

    @Override
    public String visitTherefore(MathAntlersParser.ThereforeContext ctx) {
        return " \\therefore ";
    }

    @Override
    public String visitNegate(MathAntlersParser.NegateContext ctx) {
        return "-" + visit(ctx.expr());
    }

    @Override
    public String visitElementOf(MathAntlersParser.ElementOfContext ctx) {
        return " \\in ";
    }

    @Override
    public String visitCeil(MathAntlersParser.CeilContext ctx) {
        return " \\left\\lceil " + visit(ctx.expr()) + " \\right\\rceil ";
    }

    @Override
    public String visit(ParseTree tree) {
        if (tree == null || tree.getChildCount() == 0) return "\\text{??}";
        return super.visit(tree);
    }

    @Override
    public String visitFloor(MathAntlersParser.FloorContext ctx) {
        return " \\left\\lfloor " + visit(ctx.expr()) + " \\right\\rfloor ";
    }

    @Override
    public String visitPipe(MathAntlersParser.PipeContext ctx) {
        return " \\mid ";
    }

    @Override
    public String visitString(MathAntlersParser.StringContext ctx) {
        String text = ctx.STRING().getText();
        return " \\text{" + text.substring(1, text.length() - 1) + "} ";
    }

    @Override
    public String visitEquality(MathAntlersParser.EqualityContext ctx) {
        return visit(ctx.expr(0)) + " = " + visit(ctx.expr(1));
    }

    @Override
    public String visitExponent(MathAntlersParser.ExponentContext ctx) {
        return visit(ctx.expr(0)) + "^" + visit(ctx.expr(1));
    }

    @Override
    public String visitEllipses(MathAntlersParser.EllipsesContext ctx) {
        return "\\ldots";
    }
}
