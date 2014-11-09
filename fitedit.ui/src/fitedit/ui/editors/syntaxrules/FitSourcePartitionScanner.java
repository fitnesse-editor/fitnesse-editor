package fitedit.ui.editors.syntaxrules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class FitSourcePartitionScanner extends RuleBasedPartitionScanner {
    public final static String FIT_COMMENT = "__fit_comment";
    public static final String FIT_DEFINE = "__fit_define";
    public final static String FIT_INCLUDE = "__fit_include";
    public static final String FIT_TABLE = "__fit_table";

    public FitSourcePartitionScanner() {
        class FitTableRule extends SingleLineRule {
            public FitTableRule(IToken token) {
                super("!|", "|", token);
            }

            @Override
            protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
                if (resume) {
                    char[] pipe = "|".toCharArray();

                    int c;
                    while ((c = scanner.read()) != ICharacterScanner.EOF) {
                        boolean lineDelim = false;
                        for (int i = 0; i < getLegalLineDelimiters().length; i++) {
                            if (c == getLegalLineDelimiters()[i][0]
                                    && sequenceDetected(scanner, getLegalLineDelimiters()[i], fBreaksOnEOF)) {
                                lineDelim = true;
                            }
                        }
                        if (!lineDelim) {
                            scanner.unread();
                            break;
                        }
                    }

                    c = scanner.read();
                    if (c != -1) {
                        System.out.println(Character.getName(c));
                    }
                    if (c == pipe[0]) {
                        if (sequenceDetected(scanner, pipe, false)) {
                            if (endSequenceDetected(scanner)) {
                                IToken doEvaluate = doEvaluate(scanner, true);
                                scanner.unread();
                                return doEvaluate;
                            }
                        }
                    } else {
                        return fToken;
                    }

                } else {
                    int c = scanner.read();
                    if (c == fStartSequence[0]) {
                        if (sequenceDetected(scanner, fStartSequence, false)) {
                            if (endSequenceDetected(scanner)) {
                                return doEvaluate(scanner, true);
                            }
                        }
                    }
                }
                scanner.unread();
                return Token.UNDEFINED;
            }

        }

        List<IPredicateRule> rules = new ArrayList<IPredicateRule>();
        rules.add(newEndOfLineRule("#", FIT_COMMENT));
        rules.add(new MultiLineRule("!define ", ")", new Token(FIT_DEFINE)));
        rules.add(new MultiLineRule("!define ", "}", new Token(FIT_DEFINE)));
        rules.add(newEndOfLineRule("!include", FIT_INCLUDE));
        rules.add(new FitTableRule(new Token(FIT_TABLE)));
        setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));

    }

    private EndOfLineRule newEndOfLineRule(String sequence, String token) {
        return new EndOfLineRule(sequence, new Token(token));
    }

    private SingleLineRule newSingleLineRule(String startSequence, String endSequence, String token) {
        return new SingleLineRule(startSequence, endSequence, new Token(token));
    }
}
