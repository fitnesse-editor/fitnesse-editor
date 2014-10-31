package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import fitedit.Constants;
import fitedit.FitEditPlugin;

public class PageHyperlink extends AbstractHyperLink {

    private String page;

    public PageHyperlink(String page, int offset, int length) {
        super(offset, length);
        this.page = page;
    }

    @Override
    public String getTypeLabel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHyperlinkText() {
        // TODO Auto-generated method stub
        return null;
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
        IFile extractResource = extractFile(ieditorpart);
        IProject project = extractResource.getProject();

        IPath path;
        if (page.startsWith(".")) {
            path = project.getProjectRelativePath().append(Constants.FITNESSE_ROOT).addTrailingSeparator()
                    .append(page.substring(1));
        } else {
            path = extractResource.getFullPath().removeFirstSegments(1).removeLastSegments(2).addTrailingSeparator()
                    .append(page);
        }
        path = path.addTrailingSeparator().append("content.txt");

        IFile file = project.getFile(path);

        try {
            iworkbenchpage.openEditor(new FileEditorInput(file), "fitedit.editors.FitnesseEditor");
        } catch (PartInitException e) {
            ErrorDialog.openError(null, "title", "message", new Status(IStatus.ERROR, FitEditPlugin.PLUGIN_ID,
                    "status message", e));
        }

    }
}
