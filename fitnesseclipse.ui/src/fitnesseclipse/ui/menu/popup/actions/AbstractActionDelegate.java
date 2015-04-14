package fitnesseclipse.ui.menu.popup.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;

import fitnesseclipse.ui.utils.FitUtil;

public abstract class AbstractActionDelegate implements IActionDelegate {

    protected ISelection selection = null;

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    @Override
    public void run(IAction action) {
        if (hasInvalidSelection()) {
            return;
        }

        IResource resource = FitUtil.getResource(selection);

        if (hasInalidResource(resource)) {
            return;
        }

        doRun(resource);
    }

    abstract void doRun(IResource resource);

    private boolean hasInvalidSelection() {
        return selection == null;
    }

    private boolean hasInalidResource(IResource resource) {
        return resource == null;
    }

}
