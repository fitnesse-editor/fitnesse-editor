package fitnesseclipse.ui.editors.syntaxrules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

class TableRule extends StartAndEndLineRule {

    public TableRule(IToken token) {
        super("|", "|", token);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        if (resume) {
            if (super.evaluate(scanner, true) == this.token) {
                return evaluate(scanner, true);
            } else {
                return this.token;
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