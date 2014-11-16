package fitnesseclipse.internal.core.builder;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import fitnesseclipse.core.FiteditCore;

public class PageChecker {
    private static final String INCLUDE = "!include ";

    public static void check(IProject project, IResource resource) {
        System.out.println(resource);

        if (resource.getFullPath().segmentCount() < 2) {
            return;
        }
        if (!resource.getFullPath().removeFirstSegments(1).segment(0).startsWith(fitnesseRoot())) {
            return;
        }
        if (!resource.getFullPath().lastSegment().endsWith("content.txt")) {
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
                    boolean child = false;
                    boolean parent = false;
                    if (page.contains(">")) {
                        page = page.substring(1);
                        child = true;
                    } else if (page.contains("<")) {
                        page = page.substring(1);
                        parent = true;
                    }

                    IPath path;
                    if (page.startsWith(".")) {
                        path = project.getProjectRelativePath().append(fitnesseRoot()).addTrailingSeparator()
                                .append(page.substring(1).replaceAll("\\.", "/"));
                    } else {
                        IPath projectRelativePath = resource.getProjectRelativePath();

                        int segmentsToRemove = 2;
                        if (child) {
                            segmentsToRemove = 1;
                        } else if (parent) {
                            String string = page.split("\\.")[0];

                            String[] segments = projectRelativePath.segments();
                            for (int i = 0; i < segments.length; i++) {
                                String segment = segments[i];
                                if (segment.equals(string)) {
                                    segmentsToRemove = segments.length - i;
                                    break;
                                }
                            }
                        }

                        path = projectRelativePath.removeLastSegments(segmentsToRemove).addTrailingSeparator()
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

    private static String fitnesseRoot() {
        return FiteditCore.getFiteditCore().getFitnesseRoot();
    }
}
