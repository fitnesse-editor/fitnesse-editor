package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorPart;

import fitedit.ui.utils.DialogUtils;

/**
 * Hyper Link which opens the java editor with the given class.
 * 
 * @author Andrew Holland (a1dutch)
 * @since 2.0
 */
public class ClassHyperlink extends AbstractHyperLink {

    private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
    private String fqdn;

    /**
     * 
     * @param page
     *            the page to link to.
     * @param offset
     *            the document offset.
     * @param length
     *            the length from offset.
     */
    public ClassHyperlink(String fqdn, int offset, int length) {
        super(offset, length);
        this.fqdn = fqdn;
    }

    @Override
    public void open() {
        IProject project = extractFileFromEditor(getActiveEditor()).getProject();

        try {
            if (project.getNature(JDT_NATURE) != null) {
                IJavaProject create = JavaCore.create(project);
                IType findType = create.findType(fqdn, new NullProgressMonitor());
                if (findType != null && findType instanceof IOpenable) {
                    ((IOpenable) findType).open(new NullProgressMonitor());
                }
                IEditorPart editor = JavaUI.openInEditor(findType.getCompilationUnit() != null ? findType
                        .getCompilationUnit() : findType.getClassFile());
                JavaUI.revealInEditor(editor, (IJavaElement) findType);
            }
        } catch (CoreException e) {
            DialogUtils.openErrorDialog("title", "message", "status message", e);
        }
    }
}
