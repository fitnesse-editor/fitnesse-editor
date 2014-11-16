package fitnesseclipse.internal.core;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import fitnesseclipse.core.IFitnessePage;

public abstract class AbstractFitnessePage implements IFile, IFitnessePage {

    protected IFile delegate;

    public AbstractFitnessePage(IFile delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
        return delegate.getAdapter(adapter);
    }

    @Override
    public boolean contains(ISchedulingRule rule) {
        return delegate.contains(rule);
    }

    @Override
    public boolean isConflicting(ISchedulingRule rule) {
        return delegate.isConflicting(rule);
    }

    @Override
    public void appendContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        delegate.appendContents(source, force, keepHistory, monitor);
    }

    @Override
    public void appendContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.appendContents(source, updateFlags, monitor);
    }

    @Override
    public void create(InputStream source, boolean force, IProgressMonitor monitor) throws CoreException {
        delegate.create(source, force, monitor);
    }

    @Override
    public void create(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.create(source, updateFlags, monitor);
    }

    @Override
    public void accept(IResourceProxyVisitor visitor, int memberFlags) throws CoreException {
        delegate.accept(visitor, memberFlags);
    }

    @Override
    public void createLink(IPath localLocation, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.createLink(localLocation, updateFlags, monitor);
    }

    @Override
    public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags) throws CoreException {
        delegate.accept(visitor, depth, memberFlags);
    }

    @Override
    public void createLink(URI location, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.createLink(location, updateFlags, monitor);
    }

    @Override
    public void accept(IResourceVisitor visitor) throws CoreException {
        delegate.accept(visitor);
    }

    @Override
    public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
        delegate.accept(visitor, depth, includePhantoms);
    }

    @Override
    public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
        delegate.accept(visitor, depth, memberFlags);
    }

    @Override
    public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        delegate.delete(force, keepHistory, monitor);
    }

    @Override
    public String getCharset() throws CoreException {
        return delegate.getCharset();
    }

    @Override
    public void clearHistory(IProgressMonitor monitor) throws CoreException {
        delegate.clearHistory(monitor);
    }

    @Override
    public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        delegate.copy(destination, force, monitor);
    }

    @Override
    public String getCharset(boolean checkImplicit) throws CoreException {
        return delegate.getCharset(checkImplicit);
    }

    @Override
    public String getCharsetFor(Reader reader) throws CoreException {
        return delegate.getCharsetFor(reader);
    }

    @Override
    public void copy(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.copy(destination, updateFlags, monitor);
    }

    @Override
    public IContentDescription getContentDescription() throws CoreException {
        return delegate.getContentDescription();
    }

    @Override
    public InputStream getContents() throws CoreException {
        return delegate.getContents();
    }

    @Override
    public InputStream getContents(boolean force) throws CoreException {
        return delegate.getContents(force);
    }

    @Override
    @Deprecated
    public int getEncoding() throws CoreException {
        return delegate.getEncoding();
    }

    @Override
    public IPath getFullPath() {
        return delegate.getFullPath();
    }

    @Override
    public IFileState[] getHistory(IProgressMonitor monitor) throws CoreException {
        return delegate.getHistory(monitor);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public void copy(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
        delegate.copy(description, force, monitor);
    }

    @Override
    public boolean isReadOnly() {
        return delegate.isReadOnly();
    }

    @Override
    public void move(IPath destination, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        delegate.move(destination, force, keepHistory, monitor);
    }

    @Override
    public void copy(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.copy(description, updateFlags, monitor);
    }

    @Override
    @Deprecated
    public void setCharset(String newCharset) throws CoreException {
        delegate.setCharset(newCharset);
    }

    @Override
    public void setCharset(String newCharset, IProgressMonitor monitor) throws CoreException {
        delegate.setCharset(newCharset, monitor);
    }

    @Override
    public void setContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        delegate.setContents(source, force, keepHistory, monitor);
    }

    @Override
    public IMarker createMarker(String type) throws CoreException {
        return delegate.createMarker(type);
    }

    @Override
    public void setContents(IFileState source, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        delegate.setContents(source, force, keepHistory, monitor);
    }

    @Override
    public IResourceProxy createProxy() {
        return delegate.createProxy();
    }

    @Override
    public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
        delegate.delete(force, monitor);
    }

    @Override
    public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.delete(updateFlags, monitor);
    }

    @Override
    public void setContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.setContents(source, updateFlags, monitor);
    }

    @Override
    public void setContents(IFileState source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.setContents(source, updateFlags, monitor);
    }

    @Override
    public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        delegate.deleteMarkers(type, includeSubtypes, depth);
    }

    @Override
    public boolean exists() {
        return delegate.exists();
    }

    @Override
    public IMarker findMarker(long id) throws CoreException {
        return delegate.findMarker(id);
    }

    @Override
    public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        return delegate.findMarkers(type, includeSubtypes, depth);
    }

    @Override
    public int findMaxProblemSeverity(String type, boolean includeSubtypes, int depth) throws CoreException {
        return delegate.findMaxProblemSeverity(type, includeSubtypes, depth);
    }

    @Override
    public String getFileExtension() {
        return delegate.getFileExtension();
    }

    @Override
    public long getLocalTimeStamp() {
        return delegate.getLocalTimeStamp();
    }

    @Override
    public IPath getLocation() {
        return delegate.getLocation();
    }

    @Override
    public URI getLocationURI() {
        return delegate.getLocationURI();
    }

    @Override
    public IMarker getMarker(long id) {
        return delegate.getMarker(id);
    }

    @Override
    public long getModificationStamp() {
        return delegate.getModificationStamp();
    }

    @Override
    public IPathVariableManager getPathVariableManager() {
        return delegate.getPathVariableManager();
    }

    @Override
    public IContainer getParent() {
        return delegate.getParent();
    }

    @Override
    public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
        return delegate.getPersistentProperties();
    }

    @Override
    public String getPersistentProperty(QualifiedName key) throws CoreException {
        return delegate.getPersistentProperty(key);
    }

    @Override
    public IProject getProject() {
        return delegate.getProject();
    }

    @Override
    public IPath getProjectRelativePath() {
        return delegate.getProjectRelativePath();
    }

    @Override
    public IPath getRawLocation() {
        return delegate.getRawLocation();
    }

    @Override
    public URI getRawLocationURI() {
        return delegate.getRawLocationURI();
    }

    @Override
    public ResourceAttributes getResourceAttributes() {
        return delegate.getResourceAttributes();
    }

    @Override
    public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
        return delegate.getSessionProperties();
    }

    @Override
    public Object getSessionProperty(QualifiedName key) throws CoreException {
        return delegate.getSessionProperty(key);
    }

    @Override
    public int getType() {
        return delegate.getType();
    }

    @Override
    public IWorkspace getWorkspace() {
        return delegate.getWorkspace();
    }

    @Override
    public boolean isAccessible() {
        return delegate.isAccessible();
    }

    @Override
    public boolean isDerived() {
        return delegate.isDerived();
    }

    @Override
    public boolean isDerived(int options) {
        return delegate.isDerived(options);
    }

    @Override
    public boolean isHidden() {
        return delegate.isHidden();
    }

    @Override
    public boolean isHidden(int options) {
        return delegate.isHidden(options);
    }

    @Override
    public boolean isLinked() {
        return delegate.isLinked();
    }

    @Override
    public boolean isVirtual() {
        return delegate.isVirtual();
    }

    @Override
    public boolean isLinked(int options) {
        return delegate.isLinked(options);
    }

    @Override
    @Deprecated
    public boolean isLocal(int depth) {
        return delegate.isLocal(depth);
    }

    @Override
    public boolean isPhantom() {
        return delegate.isPhantom();
    }

    @Override
    public boolean isSynchronized(int depth) {
        return delegate.isSynchronized(depth);
    }

    @Override
    public boolean isTeamPrivateMember() {
        return delegate.isTeamPrivateMember();
    }

    @Override
    public boolean isTeamPrivateMember(int options) {
        return delegate.isTeamPrivateMember(options);
    }

    @Override
    public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        delegate.move(destination, force, monitor);
    }

    @Override
    public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.move(destination, updateFlags, monitor);
    }

    @Override
    public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        delegate.move(description, force, keepHistory, monitor);
    }

    @Override
    public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        delegate.move(description, updateFlags, monitor);
    }

    @Override
    public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
        delegate.refreshLocal(depth, monitor);
    }

    @Override
    public void revertModificationStamp(long value) throws CoreException {
        delegate.revertModificationStamp(value);
    }

    @Override
    @Deprecated
    public void setDerived(boolean isDerived) throws CoreException {
        delegate.setDerived(isDerived);
    }

    @Override
    public void setDerived(boolean isDerived, IProgressMonitor monitor) throws CoreException {
        delegate.setDerived(isDerived, monitor);
    }

    @Override
    public void setHidden(boolean isHidden) throws CoreException {
        delegate.setHidden(isHidden);
    }

    @Override
    @Deprecated
    public void setLocal(boolean flag, int depth, IProgressMonitor monitor) throws CoreException {
        delegate.setLocal(flag, depth, monitor);
    }

    @Override
    public long setLocalTimeStamp(long value) throws CoreException {
        return delegate.setLocalTimeStamp(value);
    }

    @Override
    public void setPersistentProperty(QualifiedName key, String value) throws CoreException {
        delegate.setPersistentProperty(key, value);
    }

    @Override
    @Deprecated
    public void setReadOnly(boolean readOnly) {
        delegate.setReadOnly(readOnly);
    }

    @Override
    public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
        delegate.setResourceAttributes(attributes);
    }

    @Override
    public void setSessionProperty(QualifiedName key, Object value) throws CoreException {
        delegate.setSessionProperty(key, value);
    }

    @Override
    public void setTeamPrivateMember(boolean isTeamPrivate) throws CoreException {
        delegate.setTeamPrivateMember(isTeamPrivate);
    }

    @Override
    public void touch(IProgressMonitor monitor) throws CoreException {
        delegate.touch(monitor);
    }

    @Override
    public int compareTo(IFitnessePage o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp == 0) {
            return getFullPath().toString().compareTo(o.getFullPath().toString());
        }
        return cmp;
    }
}
