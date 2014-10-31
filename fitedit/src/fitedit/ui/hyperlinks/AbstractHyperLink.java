package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

public abstract class AbstractHyperLink implements IHyperlink {

    private IRegion region;

    public AbstractHyperLink(int offset, int length) {
        this.region = newRegion(offset, length);
    }

    @Override
    public IRegion getHyperlinkRegion() {
        return region;
    }

    IFile extractFile(IEditorPart editor) {
        IEditorInput input = editor.getEditorInput();
        if (!(input instanceof IFileEditorInput)) {
            return null;
        }
        return ((IFileEditorInput) input).getFile();
    }

    private IRegion newRegion(final int offset, final int length) {
        return new IRegion() {
            @Override
            public int getOffset() {
                return offset;
            }

            @Override
            public int getLength() {
                return length;
            }
        };
    }

}
