package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Base class implementing all the common functionality for hyperlinks.
 * 
 * @author Andrew Holland (a1dutch)
 * @since 2.0
 */
public abstract class AbstractHyperLink implements IHyperlink {

    private IRegion region;

    public AbstractHyperLink(int offset, int length) {
        this.region = newRegion(offset, length);
    }

    @Override
    public IRegion getHyperlinkRegion() {
        return region;
    }

    @Override
    public String getTypeLabel() {
        return null;
    }

    @Override
    public String getHyperlinkText() {
        return null;
    }

    IFile extractFileFromEditor(IEditorPart editor) {
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

    protected IEditorPart getActiveEditor() {
        return getWorkbenchPage().getActiveEditor();
    }

    protected IWorkbenchPage getWorkbenchPage() {
        IWorkbench iworkbench = PlatformUI.getWorkbench();
        IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
        IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
        return iworkbenchpage;
    }

}
