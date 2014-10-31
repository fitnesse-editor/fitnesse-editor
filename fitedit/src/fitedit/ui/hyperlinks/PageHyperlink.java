package fitedit.ui.hyperlinks;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import fitedit.Constants;
import fitedit.ui.utils.DialogUtils;

/**
 * A hyper link to another fitnesse page.
 * 
 * links can either be relative to the FitNesseRoot folder if prepended with a '.' or relative to the current editors
 * file.
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

    private String page;

    /**
     * 
     * @param page
     *            the page to link to.
     * @param offset
     *            the document offset.
     * @param length
     *            the length from offset.
     */
    public PageHyperlink(String page, int offset, int length) {
        super(offset, length);
        this.page = page;
    }

    @Override
    public void open() {
        IFile editorFile = extractFileFromEditor(getActiveEditor());
        IProject project = editorFile.getProject();

        IPath path;
        if (page.startsWith(".")) {
            path = project.getProjectRelativePath().append(Constants.FITNESSE_ROOT).addTrailingSeparator()
                    .append(page.substring(1));
        } else {
            path = editorFile.getFullPath().removeFirstSegments(1).removeLastSegments(2).addTrailingSeparator()
                    .append(page);
        }
        path = path.addTrailingSeparator().append("content.txt");

        IFile file = project.getFile(path);

        try {
            getWorkbenchPage().openEditor(new FileEditorInput(file), "fitedit.editors.FitnesseEditor");
        } catch (PartInitException e) {
            DialogUtils.openErrorDialog("title", "message", "status message", e);
        }
    }
}
