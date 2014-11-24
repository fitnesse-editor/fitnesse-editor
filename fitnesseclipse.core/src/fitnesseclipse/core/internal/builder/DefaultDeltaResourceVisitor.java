package fitnesseclipse.core.internal.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

class DefaultDeltaResourceVisitor extends AbstractResourceHelper implements IResourceDeltaVisitor {

    public DefaultDeltaResourceVisitor(String fitnesseRoot, IProject project) {
        super(fitnesseRoot, project);
    }

    @Override
    public boolean visit(IResourceDelta delta) throws CoreException {
        IResource resource = delta.getResource();
        if (isContentTxtFile(resource)) {
            deleteMarkers(resource);
            switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    PageChecker.check(fitnesseRoot, project, resource);
                    addPage(resource);
                    break;
                case IResourceDelta.REMOVED:
                    break;
                case IResourceDelta.CHANGED:
                    PageChecker.check(fitnesseRoot, project, resource);
                    break;
                default:
                    break;
            }
        }
        return true;
    }

}