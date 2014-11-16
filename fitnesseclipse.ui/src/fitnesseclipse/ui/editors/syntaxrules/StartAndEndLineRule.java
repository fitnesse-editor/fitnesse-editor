package fitnesseclipse.ui.editors.syntaxrules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class StartAndEndLineRule implements IPredicateRule {

    protected IToken token;
    private String startSequence;
    private String endSequence;

    public StartAndEndLineRule(String startSequence, String endSequence, IToken token) {
        this.startSequence = startSequence;
        this.endSequence = endSequence;
        this.token = token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        return evaluate(scanner, false);
    }

    @Override
    public IToken getSuccessToken() {
        return token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        LineScanner lineScanner = new LineScanner(scanner);
        String line = lineScanner.readLine();
        if (line != null && line.startsWith(startSequence)) {
            if (line.endsWith(endSequence)) {
                return token;
            }
        }
        lineScanner.unreadLine();
        return Token.UNDEFINED;
    }

    class LineScanner {
        private ICharacterScanner scanner;
        private int read;

        public LineScanner(ICharacterScanner scanner) {
            this.scanner = scanner;
        }

        public void unreadLine() {
            for (int i = 0; i < read; i++) {
                scanner.unread();
            }
        }

        public String readLine() {
            read = 0;
            StringBuilder builder = new StringBuilder();
            int c = -1;
            while ((c = scanner.read()) != -1) {
                if (c == '\r') {
                    if (scanner.read() == '\n') {
                        read++;
                    }
                    break;
                } else if (c == '\n') {
                    read++;
                    break;
                }
                builder.append((char) c);
                read++;
            }
            return builder.length() == 0 ? null : builder.toString();
        }
    }
}
