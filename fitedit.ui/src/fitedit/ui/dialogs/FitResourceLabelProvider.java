package fitedit.ui.dialogs;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import fitedit.core.IFitnessePage;
import fitedit.core.IFitnesseStaticPage;
import fitedit.core.IFitnesseSuitePage;
import fitedit.core.IFitnesseTestPage;
import fitedit.ui.FiteditUi;

public class FitResourceLabelProvider extends LabelProvider implements IStyledLabelProvider {

    private static final String CONCAT_STRING = " - ";

    private FitResourceSelectionDialog dialog;

    public FitResourceLabelProvider(FitResourceSelectionDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof IFitnesseTestPage) {
            return FiteditUi.getImageDescriptor("icons/fitedit_test_16.png").createImage();
        }
        if (element instanceof IFitnesseStaticPage) {
            return FiteditUi.getImageDescriptor("icons/fitedit_static_16.png").createImage();
        }
        if (element instanceof IFitnesseSuitePage) {
            return FiteditUi.getImageDescriptor("icons/fitedit_suite_16.png").createImage();
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof IFitnessePage) {
            return getBasicText((IFitnessePage) element);
        }

        return super.getText(element);
    }

    @Override
    public StyledString getStyledText(Object element) {
        if (element == null) {
            return new StyledString();
        }

        if (!(element instanceof IFitnessePage)) {
            return new StyledString(element.toString());
        }

        String text = getBasicText((IFitnessePage) element);

        StyledString string = new StyledString(text);
        int index = text.indexOf(CONCAT_STRING);

        if (index != -1) {
            string.setStyle(index, text.length() - index, StyledString.QUALIFIER_STYLER);
        }
        return string;
    }

    String getBasicText(IFitnessePage page) {
        if (page == null) {
            return null;
        }

        String name = page.getParent().getName();

        if (dialog.isDuplicateElement(page)) {
            name += " - " + page.getFullPath();
        }

        return name;
    }
}
