package fitnesseclipse.ui.editors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;

public class FitnesseFormattingStrategy extends ContextBasedFormattingStrategy {

    /** Documents to be formatted by this strategy */
    private final List<IDocument> documents = new LinkedList<IDocument>();

    private final WikiFormatter formatter = new WikiFormatter();

    @Override
    public String format(String arg0, boolean arg1, String arg2, int[] arg3) {
        return "orange";
    }

    @Override
    public void format() {
        super.format();
        if (documents.size() == 0) {
            return;
        }
        final IDocument document = documents.remove(0);
        String text = document.get();
        String formattedText = formatter.format(text);

        if (!text.equals(formattedText)) {
            document.set(formattedText);
        }
    }

    @Override
    public void formatterStarts(final IFormattingContext context) {
        super.formatterStarts(context);

        Object obj = context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
        if (obj instanceof IDocument) {
            documents.add((IDocument) obj);
        }
    }

    @Override
    public void formatterStops() {
        super.formatterStops();

        // fPartitions.clear();
        documents.clear();
    }

}
