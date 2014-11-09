package fitedit.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import fitedit.core.IFitnessePage;
import fitedit.ui.dialogs.FitResourceLabelProvider;
import fitedit.ui.dialogs.FitResourceSelectionDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenFitnesseResouceHandler extends AbstractHandler {

    /**
     * the command has been executed, so extract extract the needed information from the application context.
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

        FitResourceSelectionDialog dialog = new FitResourceSelectionDialog(window.getShell(), true);
        dialog.setListLabelProvider(new FitResourceLabelProvider(dialog));
        dialog.setTitle("Open FitNesse");
        int returnCode = dialog.open();

        if (returnCode != FitResourceSelectionDialog.OK) {
            return null;
        }

        IFitnessePage page = (IFitnessePage) dialog.getFirstResult();
        if (page == null) {
            return null;
        }

        try {
            IDE.openEditor(window.getActivePage(), page, true);
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        return null;
    }
}
