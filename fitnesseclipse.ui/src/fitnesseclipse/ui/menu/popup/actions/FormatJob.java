package fitnesseclipse.ui.menu.popup.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import fitnesseclipse.ui.FitnesseEclipseUi;
import fitnesseclipse.ui.editors.WikiFormatter;

public class FormatJob extends Job {

    private static final WikiFormatter formatter = new WikiFormatter();

    private final List<IFile> filesToFormat;

    public FormatJob(List<IFile> filesToFormat) {
        super("Format Fitnesse");
        this.filesToFormat = filesToFormat;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        int worked = 0;
        monitor.beginTask("Format Fitnesse", filesToFormat.size());

        for (IFile fileToFormat : filesToFormat) {
            if (monitor.isCanceled()) {
                return new Status(IStatus.OK, FitnesseEclipseUi.PLUGIN_ID, "Job Cancelled");
            }
            try {
                File file = fileToFormat.getRawLocation().toFile();
                String unformatted = FileUtils.readFileToString(file);
                String formatted = formatter.format(unformatted);

                if (!formatted.equals(unformatted)) {
                    FileUtils.writeStringToFile(file, formatted);
                }
            } catch (IOException e) {
                return new Status(IStatus.ERROR, FitnesseEclipseUi.PLUGIN_ID, "Failed to format file: "
                        + fileToFormat.getRawLocation(), e);
            }
            monitor.worked(++worked);
        }
        monitor.done();

        return new Status(IStatus.OK, FitnesseEclipseUi.PLUGIN_ID, "Format Complete");
    }

}
