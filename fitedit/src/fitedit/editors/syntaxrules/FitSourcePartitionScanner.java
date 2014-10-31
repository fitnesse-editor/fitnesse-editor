package fitedit.editors.syntaxrules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class FitSourcePartitionScanner extends RuleBasedPartitionScanner {
    public final static String FIT_COMMENT = "__fit_comment";
    public final static String FIT_INCLUDE = "__fit_include";
    public final static String FIT_TABLE_HEADER = "__fit_table_header";

    public FitSourcePartitionScanner() {
        List<IPredicateRule> rules = new ArrayList<IPredicateRule>();
        rules.add(newEndOfLineRule("#", FIT_COMMENT));
        rules.add(newEndOfLineRule("!include", FIT_INCLUDE));
        rules.add(newSingleLineRule("!|", "|", FIT_TABLE_HEADER));
        setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
    }

    private EndOfLineRule newEndOfLineRule(String sequence, String token) {
        return new EndOfLineRule(sequence, new Token(token));
    }

    private SingleLineRule newSingleLineRule(String startSequence, String endSequence, String token) {
        return new SingleLineRule(startSequence, endSequence, new Token(token));
    }
}
