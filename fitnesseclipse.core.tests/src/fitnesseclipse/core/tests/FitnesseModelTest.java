package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.junit.Test;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.FitnesseNature;
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
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(nullValue()));

        IFitnesseModel model = FitnesseModel.load();

        assertThat(model.getPages().size(), is(equalTo(0)));
        assertThat(model.getStaticPage(project, project.getFile(STATIC_PAGE).getFullPath()), is(nullValue()));
        assertThat(model.getSuitePage(project, project.getFile(SUITE_PAGE).getFullPath()), is(nullValue()));
        assertThat(model.getTestPage(project, project.getFile(TEST_PAGE).getFullPath()), is(nullValue()));
    }

    @Test
    public void shouldIndexWhenNaturePresent() throws Exception {
        IProject project = importProject(FITNESSE_PROJECT);
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(notNullValue()));

        IFitnesseModel model = FitnesseModel.load();

        assertThat(model.getPages().size(), is(equalTo(3)));
        assertThat(model.getStaticPage(project, project.getFile(STATIC_PAGE).getProjectRelativePath()),
                is(notNullValue()));
        assertThat(model.getSuitePage(project, project.getFile(SUITE_PAGE).getProjectRelativePath()),
                is(notNullValue()));
        assertThat(model.getTestPage(project, project.getFile(TEST_PAGE).getProjectRelativePath()), is(notNullValue()));
    }

    @Test
    public void shouldReturnDefaultFitnesseRoot() throws Exception {
        IFitnesseModel model = FitnesseModel.load();

        assertThat(model.getFitnesseRoot(), is(equalTo(DEFAULT_ROOT)));
        assertThat(model.getPages().size(), is(equalTo(0)));
    }

    @Test
    public void shouldReturnNonDefaultFitnesseRoot() throws Exception {
        IFitnesseModel model = FitnesseModel.load();

        assertThat(model.getFitnesseRoot(), is(equalTo(DEFAULT_ROOT)));
        assertThat(model.getPages().size(), is(equalTo(0)));

        model.setFitnesseRoot(ALT_ROOT);

        assertThat(model.getFitnesseRoot(), is(equalTo(ALT_ROOT)));
        assertThat(model.getPages().size(), is(equalTo(0)));
    }

}
