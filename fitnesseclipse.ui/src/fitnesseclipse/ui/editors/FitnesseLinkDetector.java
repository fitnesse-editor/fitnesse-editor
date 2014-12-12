package fitnesseclipse.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.IFitnessePage;
import fitnesseclipse.core.IFitnesseProject;
import fitnesseclipse.ui.editors.hyperlinks.ClassHyperlink;
import fitnesseclipse.ui.editors.hyperlinks.PageHyperlink;
import fitnesseclipse.ui.editors.syntaxrules.FitSourcePartitionScanner;
import fitnesseclipse.ui.utils.Preferences;

public class FitnesseLinkDetector extends AbstractHyperlinkDetector {

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IDocument document = textViewer.getDocument();
        int regionOffset = region.getOffset();
        ITypedRegion partition = document.getDocumentPartitioner().getPartition(regionOffset);
        int partitionOffset = partition.getOffset();

        IHyperlink hyperlink = null;
        try {
            if (FitSourcePartitionScanner.FIT_FIXTURE.equals(partition.getType())) {
                String source = document.get(partitionOffset, partition.getLength());
                String extractFqdn = extractFqdn(source);

                int min = partitionOffset + 2;
                int max = partitionOffset + extractFqdn.length();
                if (withinRange(min, max, regionOffset)) {
                    // TODO check class exists
                    hyperlink = new ClassHyperlink(extractFqdn, min, extractFqdn.length());
                }
            } else if (FitSourcePartitionScanner.FIT_INCLUDE.equals(partition.getType())) {
                String source = document.get(partitionOffset, partition.getLength()).trim();
                int len = "!include ".length();

                int min = partitionOffset + len;
                int max = partitionOffset + source.length();
                if (withinRange(min, max, regionOffset)) {

                    IFile editorFile = extractFileFromEditor(getActiveEditor());
                    IProject project = editorFile.getProject();

                    String page = source.substring(9);
                    if (page.contains("-seamless ")) {
                        page = page.substring(10);
                    } else if (page.contains("-c ")) {
                        page = page.substring(3);
                    }

                    IPath path = null;
                    if (page.startsWith(".")) {
                        path = project.getProjectRelativePath().append(Preferences.getFitnesseRoot())
                                .addTrailingSeparator().append(page.substring(1).replaceAll("\\.", "/"));
                    } else if (page.startsWith("<")) {
                        path = editorFile.getProjectRelativePath().removeLastSegments(3).addTrailingSeparator()
                                .append(page.substring(1).replaceAll("\\.", "/"));
                    } else if (page.startsWith(">")) {
                        // TODO
                    } else {
                        path = editorFile.getProjectRelativePath().removeLastSegments(2).addTrailingSeparator()
                                .append(page.replaceAll("\\.", "/"));
                    }

                    IFitnesseProject fitnesseProject = FiteditCore.create(editorFile.getProject());
                    IFitnessePage findPage = fitnesseProject.findPage(path);

                    if (findPage != null) {
                        hyperlink = new PageHyperlink(findPage, min, source.length() - len);
                    }
                }
            }
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hyperlink == null ? null : new IHyperlink[] { hyperlink };
    }

    private boolean withinRange(int min, int max, int offset) {
        return offset >= min && offset <= max;
    }

    private String extractFqdn(String source) {
        return source.replaceAll("[\\r\\n]+", "").split("\\|\\|")[0].substring(2);
    }

    IFile extractFileFromEditor(IEditorPart editor) {
        IEditorInput input = editor.getEditorInput();
        if (!(input instanceof IFileEditorInput)) {
            return null;
        }
        return ((IFileEditorInput) input).getFile();
    }

    protected IEditorPart getActiveEditor() {
        return getWorkbenchPage().getActiveEditor();
    }

    protected IWorkbenchPage getWorkbenchPage() {
        IWorkbench iworkbench = PlatformUI.getWorkbench();
        IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
        IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
        return iworkbenchpage;
    }
}
