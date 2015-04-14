package fitnesseclipse.ui.editors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;

public class WikiFormatter {
    public String format(String content) {
        Context cx = Context.enter();
        try {
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            Scriptable scope = cx.initStandardObjects();
            cx.setErrorReporter(new ErrorReporter() {

                @Override
                public void warning(String arg0, String arg1, int arg2, String arg3, int arg4) {
                    System.err.println(String.format("%s %s %s %s %s", arg0, arg1, arg2, arg3, arg4));

                }

                @Override
                public EvaluatorException runtimeError(String arg0, String arg1, int arg2, String arg3, int arg4) {
                    System.err.println(String.format("%s %s %s %s %s", arg0, arg1, arg2, arg3, arg4));
                    return null;
                }

                @Override
                public void error(String arg0, String arg1, int arg2, String arg3, int arg4) {
                    System.err.println(String.format("%s %s %s %s %s", arg0, arg1, arg2, arg3, arg4));

                }
            });

            // Make the editor's text available in javascript
            scope.put("wikiText", scope, content);

            Object result = null;
            cx.evaluateReader(scope,
                    new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("WikiFormatter.js"))),
                    "WikiFormatter.js", 0, null);
            result = cx.evaluateString(scope, "new WikiFormatter().format(wikiText);", "<cmd>", 1, null);

            // Convert the result to a string and print it.
            return Context.toString(result);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // Exit from the context.
            Context.exit();
        }

        return content;
    }
}
