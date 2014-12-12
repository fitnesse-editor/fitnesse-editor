package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.junit.Test;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.IFitnesseModel;
import fitnesseclipse.core.internal.model.FitnesseModel;

public class FitnesseModelTest extends AbstractFitnesseTest {

    @Test
    public void shouldGetModel() throws Exception {
        assertThat(FiteditCore.getFiteditCore().getModel(), is(notNullValue()));
        assertThat(FitnesseModel.getFitnesseModel(), is(notNullValue()));
    }

    @Test
    public void shouldNotIndexWhenNatureNotPresent() throws Exception {
        IProject project = importProject(GENERAL_PROJECT);
        assertNatureNotExists(project);

        FitnesseModel.load();

        assertPageSize(0);
        assertStaticPageNotExists(project, STATIC_PAGE);
        assertSuitePageNotExists(project, SUITE_PAGE);
        assertTestPageNotExists(project, TEST_PAGE);
    }

    @Test
    public void shouldIndexWhenNaturePresent() throws Exception {
        IProject project = importProject(FITNESSE_PROJECT);
        assertNatureExists(project);

        FitnesseModel.load();

        assertPageSize(5);
        assertStaticPageExists(project, STATIC_PAGE);
        assertSuitePageExists(project, SUITE_PAGE);
        assertTestPageExists(project, TEST_PAGE);
    }

    @Test
    public void shouldReturnDefaultFitnesseRoot() throws Exception {
        FitnesseModel.load();

        assertRoot(DEFAULT_ROOT);
        assertPageSize(0);
    }

    @Test
    public void shouldReturnNonDefaultFitnesseRoot() throws Exception {
        IFitnesseModel model = FitnesseModel.load();

        assertRoot(DEFAULT_ROOT);
        assertPageSize(0);

        model.setFitnesseRoot(ALT_ROOT);

        assertRoot(ALT_ROOT);
        assertPageSize(0);
    }

    @Test
    public void shouldFindPagesWithClosedProject() throws Exception {
        importProject(GENERAL_PROJECT).close(null);
        importProject(FITNESSE_PROJECT);
        FitnesseModel.load();

        assertPageSize(5);
    }

}
