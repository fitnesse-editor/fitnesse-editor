package fitedit.internal.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class FitnesseBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "fitedit.core.fitnesseBuilder";

    @Override
    protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
            throws CoreException {
        if (kind == FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);
            } else {
                incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        getProject().deleteMarkers(PageChecker.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            getProject().accept(new DefaultResourceVisitor(getProject()));
        } catch (CoreException e) {
        }
    }

    protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
        delta.accept(new DefaultDeltaVisitor(getProject()));
    }
}
