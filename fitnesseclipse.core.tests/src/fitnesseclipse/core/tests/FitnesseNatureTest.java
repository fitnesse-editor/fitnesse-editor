package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.junit.Test;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.FitnesseNature;

public class FitnesseNatureTest extends AbstractFitnesseTest {

    @Test
    public void shouldAddBuilderWhenNatureIsAdded() throws Exception {
        IProject project = ProjectUtils.importProject(GENERAL_PROJECT);
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
        IProject project = ProjectUtils.importProject(BUILDER_ONLY_PROJECT);
        assertNatureNotExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertFitnessBuilderExists(project);

        addNature(project);

        assertNatureExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertFitnessBuilderExists(project);
    }

    @Test
    public void shouldRemoveBuilderWhenNatureIsRemoved() throws Exception {
        IProject project = ProjectUtils.importProject(FITNESSE_PROJECT);
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

    private void assertBuilderLength(IProject project, int length) throws CoreException {
        assertThat(project.getDescription().getBuildSpec().length, is(equalTo(length)));
    }

    private void assertFitnessBuilderExists(IProject project) throws CoreException {
        assertThat(project.getDescription().getBuildSpec()[1].getBuilderName(), is(equalTo(FiteditCore.BUILDER_ID)));
    }

    private void assertJavaBuilderExists(IProject project) throws CoreException {
        assertThat(project.getDescription().getBuildSpec()[0].getBuilderName(), is(equalTo(JavaCore.BUILDER_ID)));
    }

    private void assertNatureExists(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(notNullValue()));
    }

    private void assertNatureNotExists(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(nullValue()));
    }

    private void assertNatureProject(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID).getProject(), is(equalTo(project)));
    }

    private void addNature(IProject project) throws CoreException {
        IProjectDescription description = project.getDescription();
        String[] oldNatures = description.getNatureIds();
        String[] newNatures = new String[oldNatures.length + 1];
        System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length);
        newNatures[newNatures.length - 1] = FitnesseNature.NATURE_ID;
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }
}
