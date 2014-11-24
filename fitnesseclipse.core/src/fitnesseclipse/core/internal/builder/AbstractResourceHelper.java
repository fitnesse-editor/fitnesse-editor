package fitnesseclipse.core.internal.builder;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.IFitnesseModel;
import fitnesseclipse.core.internal.model.FitnesseModel;

public class AbstractResourceHelper {

    protected enum PageType {
        SUITE, TEST, STATIC
    }

    // TODO use constant
    private static final String CONTENT_TXT = "content.txt";

    protected IProject project;

    protected String fitnesseRoot;

    public AbstractResourceHelper(String fitnesseRoot, IProject project) {
        this.fitnesseRoot = fitnesseRoot;
        this.project = project;
    }

    protected boolean isContentTxtFile(IResource resource) {
        if (!CONTENT_TXT.equals(resource.getName())) {
            return false;
        }

        if (resource.getFullPath().toString().indexOf(fitnesseRoot) == -1) {
            return false;
        }

        return true;
    }

    protected void deleteMarkers(IResource resource) throws CoreException {
        if (resource.exists()) {
            resource.deleteMarkers(FiteditCore.MARKER_TYPE, false, IResource.DEPTH_ZERO);
        }
    }

    protected PageType getPageType(IFile file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(file.getRawLocationURI()));
            if (document.getElementsByTagName("Suite").getLength() != 0) {
                return PageType.SUITE;
            } else if (document.getElementsByTagName("Test").getLength() != 0) {
                return PageType.TEST;
            } else {
                return PageType.STATIC;
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    protected void addPage(IResource resource) throws CoreException {
        IFile file = project.getFile(resource.getProjectRelativePath().removeLastSegments(1).append("properties.xml"));

        if (!file.exists()) {
            return;
        }
        IFitnesseModel model = FitnesseModel.getFitnesseModel();
        switch (getPageType(file)) {
            case STATIC:
                model.addStaticPage(project.getFile(resource.getProjectRelativePath()));
                break;
            case SUITE:
                model.addSuitePage(project.getFile(resource.getProjectRelativePath()));
                break;
            case TEST:
                model.addTestPage(project.getFile(resource.getProjectRelativePath()));
                break;
            default:
                break;
        }
    }

}
