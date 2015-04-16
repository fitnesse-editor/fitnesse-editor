package fitnesseclipse.ui.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.ResourceUtil;

import fitnesseclipse.ui.utils.FitUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenBrowserHandler extends AbstractHandler {

    /**
     * the command has been executed, so extract extract the needed information from the application context.
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection menuSelection = HandlerUtil.getActiveMenuSelection(event);
        if (menuSelection != null && menuSelection instanceof IStructuredSelection) {
            Iterator<?> iterator = ((IStructuredSelection) menuSelection).iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object instanceof IJavaProject) {
                    FitUtil.openFitnesseInBrowser(((IJavaProject) object).getProject());
                }
                if (object instanceof IResource) {
                    FitUtil.openFitnesseInBrowser((IResource) object);
                }
            }
        }
        if (HandlerUtil.getActiveEditor(event) != null) {
            IResource resource = ResourceUtil.getResource(HandlerUtil.getActiveEditor(event).getEditorInput());
            if (resource != null) {
                FitUtil.openFitnesseInBrowser(resource);
            }
        }
        return null;
    }
}
