package fitnesseclipse.core.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.junit.Before;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.FitnesseNature;
import fitnesseclipse.core.IFitnesseModel;
import fitnesseclipse.core.tests.helpers.JobHelper;
import fitnesseclipse.core.tests.helpers.WorkspaceHelper;

public class AbstractFitnesseTest {

    public static final String ALT_ROOT = "FitNesseRootAlt";
    public static final String BUILDER_ONLY_PROJECT = "builder-only-project";
    public static final String DEFAULT_ROOT = "FitNesseRoot";

    public static final String FITNESSE_PROJECT = "fitnesse-project";
    public static final String GENERAL_PROJECT = "general-project";

    public static final String STATIC_PAGE = "/FitNesseRoot/StaticPage";
    public static final String SUITE_PAGE = "/FitNesseRoot/SuitePage";
    public static final String TEST_PAGE = "/FitNesseRoot/TestPage";

    public IProject importProject(String projectName) throws Exception {
        System.out.println("importing: " + projectName);
        IProject project = ProjectUtils.importProject(projectName);
        JobHelper.waitForJobsToComplete();
        System.out.println("imported: " + projectName);
        return project;
    }

    @Before
    public void setup() throws Exception {
        WorkspaceHelper.cleanWorkspace();
    }

    protected void assertBuilderLength(IProject project, int length) throws CoreException {
        assertThat(project.getDescription().getBuildSpec().length, is(equalTo(length)));
    }

    protected void assertFitnessBuilderExists(IProject project) throws CoreException {
        assertThat(project.getDescription().getBuildSpec()[1].getBuilderName(), is(equalTo(FiteditCore.BUILDER_ID)));
    }

    protected void assertJavaBuilderExists(IProject project) throws CoreException {
        assertThat(project.getDescription().getBuildSpec()[0].getBuilderName(), is(equalTo(JavaCore.BUILDER_ID)));
    }

    protected void assertNatureExists(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(notNullValue()));
    }

    protected void assertNatureNotExists(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID), is(nullValue()));
    }

    protected void assertNatureProject(IProject project) throws CoreException {
        assertThat(project.getNature(FitnesseNature.NATURE_ID).getProject(), is(equalTo(project)));
    }

    protected void assertPageSize(int expectedPages) throws CoreException {
        assertThat(getModel().getPages().size(), is(expectedPages));
    }

    protected void assertStaticPageExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getStaticPage(project, project.getFile(page).getProjectRelativePath()), is(notNullValue()));
    }

    protected void assertStaticPageNotExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getStaticPage(project, project.getFile(page).getProjectRelativePath()), is(nullValue()));
    }

    protected void assertSuitePageExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getSuitePage(project, project.getFile(page).getProjectRelativePath()), is(notNullValue()));
    }

    protected void assertSuitePageNotExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getSuitePage(project, project.getFile(page).getProjectRelativePath()), is(nullValue()));
    }

    protected void assertTestPageExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getTestPage(project, project.getFile(page).getProjectRelativePath()), is(notNullValue()));
    }

    protected void assertTestPageNotExists(IProject project, String page) throws CoreException {
        IFitnesseModel model = getModel();
        assertThat(model.getTestPage(project, project.getFile(page).getFullPath()), is(nullValue()));
    }

    protected void assertRoot(String root) throws CoreException {
        assertThat(getModel().getFitnesseRoot(), is(equalTo(root)));
    }

    private IFitnesseModel getModel() throws CoreException {
        return FiteditCore.getFiteditCore().getModel();
    }
}
