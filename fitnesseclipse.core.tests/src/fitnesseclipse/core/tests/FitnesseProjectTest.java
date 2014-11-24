package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

import fitnesseclipse.core.FiteditCore;
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

        assertThat(project.findSuitePage(new Path(SUITE_PAGE)), is(notNullValue()));
    }

    @Test
    public void shouldFindStaticPage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertThat(project.findStaticPage(new Path(STATIC_PAGE)), is(notNullValue()));
    }

    @Test
    public void shouldFindTestPage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertThat(project.findTestPage(new Path(TEST_PAGE)), is(notNullValue()));
    }

    @Test
    public void shouldFindPage() throws Exception {
        IFitnesseProject project = FiteditCore.create(importProject(FITNESSE_PROJECT));

        assertThat(project.findPage(new Path(STATIC_PAGE)), is(notNullValue()));
        assertThat(project.findPage(new Path(SUITE_PAGE)), is(notNullValue()));
        assertThat(project.findPage(new Path(TEST_PAGE)), is(notNullValue()));
    }
}
