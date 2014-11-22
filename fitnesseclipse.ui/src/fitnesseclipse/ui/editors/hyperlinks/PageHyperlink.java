package fitnesseclipse.ui.editors.hyperlinks;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import fitnesseclipse.core.IFitnessePage;
import fitnesseclipse.ui.editors.FitnesseEditor;
import fitnesseclipse.ui.utils.DialogUtils;

/**
 * A hyper link to another fitnesse page. links can either be relative to the FitNesseRoot folder if prepended with a
 * '.' or relative to the current editors file.
 * <p>
 * Given the following FitNesseRoot:
 *
 * <pre>
 * MySuite.MyTest
 * MySuite.MyIncludedTest
 * MyOtherSuite.MyOtherTest
 * </pre>
 *
 * e.g. an !include from MySuite.MyTest to MySuite.MyIncludedTest could be written as !Include MyIncludedTest
 * <p>
 * e.g. an include from MyOtherSuite.MyOtherTest to MySuite.MyIncludedTest would be written as !Include
 * .MySuite.MyIncludedTest
 *
 * @author Andrew Holland (a1dutch)
 * @since 2.0
 */
public class PageHyperlink extends AbstractHyperLink {

    private IFitnessePage page;

    /**
     * Creates a hyper link to the specified page with offset position and content length.
     *
     * @param findPage
     *            the page to link to.
     * @param offset
     *            the document offset.
     * @param length
     *            the length from offset.
     */
    public PageHyperlink(IFitnessePage findPage, int offset, int length) {
        super(offset, length);
        this.page = findPage;
    }

    @Override
    public void open() {
        try {
            getWorkbenchPage().openEditor(new FileEditorInput(page.getFile()), FitnesseEditor.EDITOR_ID);
        } catch (PartInitException e) {
            DialogUtils.openErrorDialog("title", "message", "status message", e);
        }
    }
}
