package fitnesseclipse.internal.core.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

class DefaultDeltaResourceVisitor extends AbstractResourceHelper implements IResourceDeltaVisitor {

    public DefaultDeltaResourceVisitor(IProject project) {
        super(project);
    }

    @Override
    public boolean visit(IResourceDelta delta) throws CoreException {
        IResource resource = delta.getResource();
        if (isContentTxtFile(resource)) {
            deleteMarkers(resource);
            switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    PageChecker.check(project, resource);
                    addPage(resource);
                    break;
                case IResourceDelta.REMOVED:
                    break;
                case IResourceDelta.CHANGED:
                    PageChecker.check(project, resource);
                    break;
                default:
                    break;
            }
        }
        return true;
    }

}