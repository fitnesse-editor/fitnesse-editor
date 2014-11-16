package fitnesseclipse.ui.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

public class FitnesseContentTypeDescriber implements ITextContentDescriber {

    @Override
    public int describe(InputStream contents, IContentDescription description) throws IOException {
        return describe(new InputStreamReader(contents), description);
    }

    @Override
    public QualifiedName[] getSupportedOptions() {
        return null;
    }

    @Override
    public int describe(Reader contents, IContentDescription description) throws IOException {
        // TODO Auto-generated method stub
        return IContentDescriber.VALID;
    }

}
