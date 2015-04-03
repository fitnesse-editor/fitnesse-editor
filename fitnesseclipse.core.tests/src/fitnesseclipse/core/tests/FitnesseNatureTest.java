package fitnesseclipse.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

import fitnesseclipse.core.IFitnesseNature;

public class FitnesseNatureTest extends AbstractFitnesseTest {

    @Test
    public void shouldAddBuilderWhenNatureIsAdded() throws Exception {
        IProject project = importProject(GENERAL_PROJECT);
        assertBuilderLength(project, 1);
        assertNatureNotExists(project);

        addNature(project);

        assertNatureExists(project);
        assertNatureProject(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertFitnessBuilderExists(project);
    }

    @Test
    public void shouldNotAddBuilderWhenBuilderExists() throws Exception {
        IProject project = importProject(BUILDER_ONLY_PROJECT);
        assertNatureNotExists(project);
        assertBuilderLength(project, 1);
        assertJavaBuilderExists(project);

        addNature(project);

        assertNatureExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertFitnessBuilderExists(project);
    }

    @Test
    public void shouldRemoveBuilderWhenNatureIsRemoved() throws Exception {
        IProject project = importProject(FITNESSE_PROJECT);
        assertNatureExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertFitnessBuilderExists(project);

        removeNature(project);

        assertNatureNotExists(project);
        assertBuilderLength(project, 1);
        assertJavaBuilderExists(project);
    }

    private void removeNature(IProject project) throws CoreException {
        IProjectDescription description = project.getDescription();
        String[] oldNatures = description.getNatureIds();
        String[] newNatures = new String[oldNatures.length - 1];
        System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length - 1);
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }

    private void addNature(IProject project) throws CoreException {
        IProjectDescription description = project.getDescription();
        String[] oldNatures = description.getNatureIds();
        String[] newNatures = new String[oldNatures.length + 1];
        System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length);
        newNatures[newNatures.length - 1] = IFitnesseNature.NATURE_ID;
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }
}
