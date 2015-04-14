package fitnesseclipse.ui.dialogs;

import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import fitnesseclipse.core.FitnesseEclipseCore;
import fitnesseclipse.core.IFitnessePage;
import fitnesseclipse.ui.FitnesseEclipseUi;

public class FitResourceSelectionDialog extends FilteredItemsSelectionDialog {

    public FitResourceSelectionDialog(Shell shell, boolean b) {
        super(shell, b);
        setImage(FitnesseEclipseUi.getImageDescriptor("icons/fitedit_16.png").createImage());
    }

    @Override
    protected Control createExtendedContentArea(Composite parent) {
        return null;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings = FitnesseEclipseUi.getDefault().getDialogSettings().getSection("section");
        if (settings == null) {
            settings = FitnesseEclipseUi.getDefault().getDialogSettings().addNewSection("section");
        }
        return settings;
    }

    @Override
    protected IStatus validateItem(Object item) {
        return Status.OK_STATUS;
    }

    @Override
    protected ItemsFilter createFilter() {
        return new ItemsFilter() {
            @Override
            public boolean matchItem(Object item) {
                IFitnessePage page = (IFitnessePage) item;
                return matches(page.getFile().getParent().getName());
            }

            @Override
            public boolean isConsistentItem(Object item) {
                return true;
            }
        };

    }

    @Override
    protected Comparator<?> getItemsComparator() {
        return new Comparator<IFitnessePage>() {
            @Override
            public int compare(IFitnessePage arg0, IFitnessePage arg1) {
                return arg0.compareTo(arg1);
            }
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
            IProgressMonitor progressMonitor) throws CoreException {
        progressMonitor.beginTask("Open..", 10);

        List<IFitnessePage> pages = FitnesseEclipseCore.getFiteditCore().getModel().getPages();

        int total = 0;
        int unit = pages.size() / 10;
        if (unit == 0) {
            unit = 1;
        }
        int i = 0;
        for (IFitnessePage r : pages) {
            i++;
            if (i % unit == 0) {
                total++;
                progressMonitor.worked(total);
            }
            contentProvider.add(r, itemsFilter);
        }

        progressMonitor.done();
    }

    @Override
    public String getElementName(Object item) {
        return ((IFitnessePage) item).getFile().getParent().getName();
    }

}
