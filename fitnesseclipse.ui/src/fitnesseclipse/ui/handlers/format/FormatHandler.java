package fitnesseclipse.ui.handlers.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import fitnesseclipse.core.FitnesseEclipseCore;

public class FormatHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection menuSelection = HandlerUtil.getActiveMenuSelection(event);
        if (menuSelection != null && menuSelection instanceof IStructuredSelection) {
            Iterator<?> iterator = ((IStructuredSelection) menuSelection).iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();

                try {
                    String fitnesseRoot = fitnesseRoot();
                    if (object instanceof IJavaProject) {
                        IProject project = ((IJavaProject) object).getProject();
                        IFolder root = project.getFolder(fitnesseRoot);
                        if (root.exists()) {
                            formatSources(root);
                        }
                    } else if (object instanceof IFolder) {
                        IFolder folder = (IFolder) object;
                        IFolder root = folder.getProject().getFolder(fitnesseRoot);
                        if (root.exists() && root.getProjectRelativePath().isPrefixOf(folder.getProjectRelativePath())) {
                            formatSources(folder);
                        }
                    } else if (object instanceof IFile) {
                        IFile file = (IFile) object;
                        IFolder root = file.getProject().getFolder(fitnesseRoot);
                        if (root.exists() && root.getProjectRelativePath().isPrefixOf(file.getProjectRelativePath())) {
                            formatSources(Arrays.asList(file));
                        }
                    }
                } catch (CoreException e) {
                    throw new ExecutionException("Failed to format", e);
                }
            }
        }
        IEditorPart editor = HandlerUtil.getActiveEditor(event);
        if (editor != null) {
            ((ITextOperationTarget) editor.getAdapter(ITextOperationTarget.class)).doOperation(ISourceViewer.FORMAT);
        }
        return null;
    }

    private void formatSources(IFolder object) throws CoreException {
        List<IFile> filesToFormat = new ArrayList<>();
        object.accept(new FormatResourceVisitor(filesToFormat));
        formatSources(filesToFormat);
    }

    private void formatSources(List<IFile> filesToFormat) throws CoreException {
        new FormatJob(filesToFormat).schedule();
    }

    private String fitnesseRoot() throws CoreException {
        return FitnesseEclipseCore.getFiteditCore().getModel().getFitnesseRoot();
    }

}
