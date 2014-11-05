package fitedit.internal.core.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

class DefaultDeltaVisitor implements IResourceDeltaVisitor {
    private IProject project;

    public DefaultDeltaVisitor(IProject project) {
        this.project = project;
    }

    @Override
    public boolean visit(IResourceDelta delta) throws CoreException {
        IResource resource = delta.getResource();
        resource.deleteMarkers(PageChecker.MARKER_TYPE, false, IResource.DEPTH_ZERO);
        switch (delta.getKind()) {
        case IResourceDelta.ADDED:
            PageChecker.check(project, resource);
            break;
        case IResourceDelta.REMOVED:
            break;
        case IResourceDelta.CHANGED:
            PageChecker.check(project, resource);
            break;
        default:
            break;
        }
        return true;
    }
}