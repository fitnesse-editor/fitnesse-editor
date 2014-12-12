package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.IFitnessePage;
import fitnesseclipse.core.IFitnesseProject;

public class FitnesseProjectTest extends AbstractFitnesseTest {
    @Test
    public void shouldNotCreateFitnessProjectWhenNatureNotPresent() throws Exception {
        IProject project = importProject(GENERAL_PROJECT);

        assertThat(FiteditCore.create(project), is(nullValue()));
    }

    @Test
    public void shouldCreateFitnessProjectWhenNaturePresent() throws Exception {
        IProject project = importProject(FITNESSE_PROJECT);

        assertThat(FiteditCore.create(project), is(notNullValue()));
    }

    @Test
    public void shouldFindSuitePage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertPageWithNoMarkers(project, SUITE_PAGE);
    }

    @Test
    public void shouldFindStaticPage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertPageWithNoMarkers(project, STATIC_PAGE);
    }

    @Test
    public void shouldFindTestPage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertPageWithNoMarkers(project, TEST_PAGE);
    }

    @Test
    public void shouldFindPages() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertPageWithNoMarkers(project, STATIC_PAGE);
        assertPageWithNoMarkers(project, SUITE_PAGE);
        assertPageWithNoMarkers(project, TEST_PAGE);
    }

    private void assertPageWithNoMarkers(IFitnesseProject project, String page) throws Exception {
        IFitnessePage findPage = project.findPage(new Path(SUITE_PAGE));

        assertThat(findPage, is(notNullValue()));
        assertThat(fitnesseMarkers(findPage).length, is(0));
    }

    private IMarker[] fitnesseMarkers(IFitnessePage page) throws CoreException {
        return page.getFile().findMarkers(FiteditCore.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }
}
