package fitnesseclipse.ui.editors.syntaxrules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

class FitTableRule extends StartAndEndLineRule {

    private StartAndEndLineRule startAndEndLineRule;

    public FitTableRule(IToken token) {
        super("!|", "|", token);

        startAndEndLineRule = new StartAndEndLineRule("|", "|", token);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        if (resume) {
            IToken token = startAndEndLineRule.evaluate(scanner, true);
            if (this.token == token) {
                return startAndEndLineRule.evaluate(scanner, true);
            } else {
                return token;
            }
        } else {
            IToken token = super.evaluate(scanner, resume);
            if (this.token == token) {
                return evaluate(scanner, true);
            }
        }
        return Token.UNDEFINED;
    }
}