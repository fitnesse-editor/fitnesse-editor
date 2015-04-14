package fitnesseclipse.ui.menu.popup.actions;

import org.eclipse.core.resources.IResource;

import fitnesseclipse.ui.utils.FitUtil;

public class OpenBrowserAction extends AbstractActionDelegate {

    @Override
    void doRun(IResource resource) {
        FitUtil.openFitnesseInBrowser(resource);
    }

}
