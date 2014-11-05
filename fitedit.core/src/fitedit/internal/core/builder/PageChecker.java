package fitedit.internal.core.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

public class PageChecker {
    private static final String FITNESSE_ROOT = "FitNesseRoot";
    private static final String INCLUDE = "!include ";
    public static final String MARKER_TYPE = "fitedit.core.marker.pageMissing";

    public static void check(IProject project, IResource resource) {
        System.out.println(resource);

        if (resource.getFullPath().segmentCount() < 2) {
            return;
        }
        if (!resource.getFullPath().removeFirstSegments(1).segment(0).startsWith(FITNESSE_ROOT)) {
            return;
        }
        if (!resource.getFullPath().lastSegment().endsWith("content.txt")) {
            return;
        }

        try {
            int read = 1;
            int chars = 0;
            String line;
            new File("./").getAbsolutePath();
            BufferedReader reader = new BufferedReader(new FileReader(resource.getRawLocation().toFile()));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(INCLUDE)) {
                    String page = line.substring(INCLUDE.length());
                    IPath path;
                    if (page.startsWith(".")) {
                        path = project.getProjectRelativePath().append(FITNESSE_ROOT).addTrailingSeparator()
                                .append(page.substring(1));
                    } else {
                        path = resource.getFullPath().removeFirstSegments(1).removeLastSegments(2)
                                .addTrailingSeparator().append(page);
                    }
                    path = path.append("content.txt");

                    IFile file = project.getFile(path);
                    if (!file.exists()) {
                        IMarker marker = resource.createMarker(MARKER_TYPE);
                        marker.setAttribute(IMarker.MESSAGE, "page (" + page + ") does not exist");
                        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                        marker.setAttribute(IMarker.CHAR_START, chars + INCLUDE.length());
                        marker.setAttribute(IMarker.CHAR_END, chars + line.length());
                        marker.setAttribute(IMarker.LINE_NUMBER, read);
                    }
                }
                chars += line.length() + System.lineSeparator().length();
                read++;
            }
            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
