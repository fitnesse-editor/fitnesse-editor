package fitedit.ui.dialogs;

import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import fitedit.ui.FiteditUi;
import fitedit.ui.resource.FitResource;
import fitedit.ui.resource.FitResourceManager;

public class FitResourceSelectionDialog extends FilteredItemsSelectionDialog {

    public FitResourceSelectionDialog(Shell shell, boolean b) {
        super(shell, b);
    }

    @Override
    protected Control createExtendedContentArea(Composite parent) {
        return null;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings = FiteditUi.getDefault().getDialogSettings().getSection("section");
        if (settings == null) {
            settings = FiteditUi.getDefault().getDialogSettings().addNewSection("section");
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
                FitResource r = (FitResource) item;
                return matches(r.getName());
            }

            @Override
            public boolean isConsistentItem(Object item) {
                return true;
            }
        };

    }

    @Override
    protected Comparator<?> getItemsComparator() {
        return new Comparator<Object>() {
            @Override
            public int compare(Object arg0, Object arg1) {
                FitResource a = (FitResource) arg0;
                FitResource b = (FitResource) arg1;
                return a.compareTo(b);
            }
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
            IProgressMonitor progressMonitor) throws CoreException {
        progressMonitor.beginTask("Open..", 10);

        int total = 0;
        int unit = FitResourceManager.getInstance().getResouces().size() / 10;
        if (unit == 0) {
            unit = 1;
        }
        int i = 0;
        for (FitResource r : FitResourceManager.getInstance().getResouces()) {
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
        FitResource r = (FitResource) item;
        return r.getName();
    }

}
