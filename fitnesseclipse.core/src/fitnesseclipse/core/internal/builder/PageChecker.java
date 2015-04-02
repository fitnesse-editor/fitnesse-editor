package fitnesseclipse.core.internal.builder;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.logging.ILogger;
import fitnesseclipse.logging.LoggerFactory;

public class PageChecker {

    private static final ILogger logger = LoggerFactory.getLogger(PageChecker.class);

    private static final String INCLUDE = "!include ";

    public static void check(String fitnesseRoot, IProject project, IResource resource) {
        logger.debug("Checking Resource: " + resource.getFullPath());

        String resourcePath = resource.getFullPath().toString();
        if (!(resourcePath.indexOf(fitnesseRoot) != -1)) {
            return;
        }
        if (!resourcePath.endsWith("content.txt")) {
            return;
        }

        try {
            int read = 1;
            int chars = 0;
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(resource.getRawLocation().toFile()));
            boolean withinDefine = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("!define")) {
                    withinDefine = true;
                }
                if (line.endsWith("}") || line.endsWith(")")) {
                    withinDefine = false;
                    continue;
                }
                if (!withinDefine && line.startsWith(INCLUDE)) {
                    String page = line.substring(INCLUDE.length());
                    if (page.contains("-seamless ")) {
                        page = page.substring(10);
                    } else if (page.contains("-c ")) {
                        page = page.substring(3);
                    }

                    IPath path = null;
                    if (page.startsWith(".")) {
                        path = project.getProjectRelativePath().append(fitnesseRoot).addTrailingSeparator()
                                .append(page.substring(1).replaceAll("\\.", "/"));
                    } else if (page.startsWith("<")) {
                        int segmentsToRemove = 0;
                        String pagePath = page.substring(1).replaceAll("\\.", "/").concat("/content.txt");
                        while (segmentsToRemove != resource.getProjectRelativePath().segmentCount()) {
                            IPath pathToTry = resource.getProjectRelativePath().removeLastSegments(++segmentsToRemove)
                                    .append(pagePath);
                            if (project.getFile(pathToTry).exists()) {
                                path = pathToTry.removeLastSegments(1);
                                break;
                            }
                        }
                    } else if (page.startsWith(">")) {

                    } else {
                        path = resource.getProjectRelativePath().removeLastSegments(2).addTrailingSeparator()
                                .append(page.replaceAll("\\.", "/"));
                    }
                    path = path.append("content.txt");

                    IFile file = project.getFile(path);
                    if (!file.exists()) {
                        IMarker marker = resource.createMarker(FiteditCore.MARKER_TYPE);
                        marker.setAttribute(IMarker.MESSAGE, "page (" + file.getProjectRelativePath().toString()
                                + ") does not exist");
                        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                        marker.setAttribute(IMarker.CHAR_START, chars + INCLUDE.length());
                        marker.setAttribute(IMarker.CHAR_END, chars + line.length());
                        marker.setAttribute(IMarker.LINE_NUMBER, read);
                        marker.setAttribute("page", file.getProjectRelativePath().toString());
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
