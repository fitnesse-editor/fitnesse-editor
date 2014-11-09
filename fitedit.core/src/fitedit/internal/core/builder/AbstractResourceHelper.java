package fitedit.internal.core.builder;

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

import fitedit.core.FiteditCore;
import fitedit.core.FitnesseModel;

public class AbstractResourceHelper {

    protected enum PageType {
        SUITE, TEST, STATIC
    }

    // TODO use constant
    private static final String CONTENT_TXT = "content.txt";

    protected IProject project;

    public AbstractResourceHelper(IProject project) {
        this.project = project;
    }

    protected boolean isContentTxtFile(IResource resource) {
        if (!CONTENT_TXT.equals(resource.getName())) {
            return false;
        }

        if (resource.getFullPath().toString().indexOf(FiteditCore.getFiteditCore().getFitnesseRoot()) == -1) {
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

    protected void addPage(IResource resource) {
        IFile file = project.getFile(resource.getProjectRelativePath().removeLastSegments(1).append("properties.xml"));

        if (!file.exists()) {
            return;
        }

        switch (getPageType(file)) {
            case STATIC:
                FitnesseModel.addStaticPage(project.getFile(resource.getProjectRelativePath()), resource.getProject());
                break;
            case SUITE:
                FitnesseModel.addSuitePage(project.getFile(resource.getProjectRelativePath()), resource.getProject());
                break;
            case TEST:
                FitnesseModel.addTestPage(project.getFile(resource.getProjectRelativePath()), resource.getProject());
                break;
            default:
                break;
        }
    }

}
