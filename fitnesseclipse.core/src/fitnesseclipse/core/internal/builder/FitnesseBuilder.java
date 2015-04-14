package fitnesseclipse.core.internal.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import fitnesseclipse.core.FitnesseEclipseCore;

public class FitnesseBuilder extends IncrementalProjectBuilder {

    @Override
    protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
            throws CoreException {
        // TODO need to extend the model to include !includes so we can update other pages without doing a full build

        // if (kind == FULL_BUILD) {
        fullBuild(monitor);
        // } else {
        // IResourceDelta delta = getDelta(getProject());
        // if (delta == null) {
        // fullBuild(monitor);
        // } else {
        // incrementalBuild(delta, monitor);
        // }
        // }
        return null;
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        getProject().deleteMarkers(FitnesseEclipseCore.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            getProject()
            .accept(new DefaultResourceVisitor(FitnesseEclipseCore.getFiteditCore().getModel().getFitnesseRoot(),
                    getProject()));
        } catch (CoreException e) {
        }
    }

    protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
        delta.accept(new DefaultDeltaResourceVisitor(FitnesseEclipseCore.getFiteditCore().getModel().getFitnesseRoot(),
                getProject()));
    }
}
