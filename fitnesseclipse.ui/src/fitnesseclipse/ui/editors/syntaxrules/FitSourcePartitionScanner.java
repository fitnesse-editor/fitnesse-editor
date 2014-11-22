package fitnesseclipse.ui.editors.syntaxrules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class FitSourcePartitionScanner extends RuleBasedPartitionScanner {
    public static final String FIT_COMMENT = "__fit_comment";
    public static final String FIT_DEFINE = "__fit_define";
    public static final String FIT_INCLUDE = "__fit_include";
    public static final String FIT_FIXTURE = "__fit_fixture";
    public static final String FIT_TABLE = "__fit_table";

    public FitSourcePartitionScanner() {
        List<IPredicateRule> rules = new ArrayList<IPredicateRule>();
        rules.add(newEndOfLineRule("#", FIT_COMMENT));
        rules.add(new MultiLineRule("!define ", ")", new Token(FIT_DEFINE)));
        rules.add(new MultiLineRule("!define ", "}", new Token(FIT_DEFINE)));
        rules.add(newEndOfLineRule("!include", FIT_INCLUDE));
        rules.add(new FixtureRule(new Token(FIT_FIXTURE)));
        rules.add(new TableRule(new Token(FIT_TABLE)));
        setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
    }

    private EndOfLineRule newEndOfLineRule(String sequence, String token) {
        return new EndOfLineRule(sequence, new Token(token));
    }
}
