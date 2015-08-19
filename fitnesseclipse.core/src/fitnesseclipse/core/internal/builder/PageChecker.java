package fitnesseclipse.core.internal.builder;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import fitnesseclipse.core.FitnesseEclipseCore;
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

        try (BufferedReader reader = new BufferedReader(new FileReader(resource.getRawLocation().toFile()))) {
            int read = 1;
            int chars = 0;
            String line;
            boolean withinDefine = false;
            while ((line = reader.readLine()) != null) {
                // TODO probably not the nicest way to handle this, can do a better job once the grappa parser is ready
                if (line.startsWith("!define") || line.startsWith("!define")) {
                    withinDefine = true;
                }
                if (withinDefine && (line.endsWith("}") || line.endsWith(")"))) {
                    withinDefine = false;
                    chars += System.lineSeparator().length() + line.length();
                    read++;
                    continue;
                }
                if (!withinDefine && line.startsWith(INCLUDE)) {
                    String page = line.substring(INCLUDE.length());
                    if (page.startsWith("-seamless ")) {
                        page = page.substring(10);
                    } else if (page.startsWith("-setup ")) {
                        page = page.substring(7);
                    } else if (page.startsWith("-teardown ")) {
                        page = page.substring(10);
                    } else if (page.startsWith("-c ")) {
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
                        // not implemented just skip over for now

                        chars += System.lineSeparator().length() + line.length();
                        read++;
                        continue;
                    } else {
                        path = resource.getProjectRelativePath().removeLastSegments(2).addTrailingSeparator()
                                .append(page.replaceAll("\\.", "/"));
                    }
                    path = path.append("content.txt");

                    IFile file = project.getFile(path);
                    if (!file.exists()) {
                        IMarker marker = resource.createMarker(FitnesseEclipseCore.MARKER_TYPE);
                        marker.setAttribute(IMarker.MESSAGE,
                                "page (" + file.getProjectRelativePath().toString() + ") does not exist");
                        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                        marker.setAttribute(IMarker.CHAR_START, chars + INCLUDE.length());
                        marker.setAttribute(IMarker.CHAR_END, chars + line.length());
                        marker.setAttribute(IMarker.LINE_NUMBER, read);
                        marker.setAttribute("page", file.getProjectRelativePath().toString());
                    }
                }
                chars += System.lineSeparator().length() + line.length();
                read++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
