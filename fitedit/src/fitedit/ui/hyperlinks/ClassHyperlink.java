package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ClassHyperlink extends AbstractHyperLink {

    private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
    private String fqdn;

    public ClassHyperlink(String fqdn, int offset, int length) {
        super(offset, length);
        this.fqdn = fqdn;
    }

    @Override
    public String getTypeLabel() {
        return "";
    }

    @Override
    public String getHyperlinkText() {
        return "";
    }

    @Override
    public void open() {
        IWorkbench iworkbench = PlatformUI.getWorkbench();
        if (iworkbench == null) {

        }
        IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
        if (iworkbenchwindow == null) {

        }
        IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
        if (iworkbenchpage == null) {

        }
        IEditorPart ieditorpart = iworkbenchpage.getActiveEditor();
        IResource extractResource = extractFile(ieditorpart);
        IProject project = extractResource.getProject();

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
                System.out.println();
            }
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
