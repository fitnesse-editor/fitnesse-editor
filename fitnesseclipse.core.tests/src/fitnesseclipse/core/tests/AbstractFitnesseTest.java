package fitnesseclipse.core.tests;

import org.eclipse.core.resources.IProject;
import org.junit.Before;

import fitnesseclipse.core.tests.helpers.JobHelper;
import fitnesseclipse.core.tests.helpers.WorkspaceHelper;

public class AbstractFitnesseTest {

    public static final String GENERAL_PROJECT = "general-project";
    public static final String FITNESSE_PROJECT = "fitnesse-project";
    public static final String BUILDER_ONLY_PROJECT = "builder-only-project";

    public static final String ALT_ROOT = "FitNesseRootAlt";
    public static final String DEFAULT_ROOT = "FitNesseRoot";

    public static final String TEST_PAGE = "/FitNesseRoot/TestPage/content.txt";
    public static final String SUITE_PAGE = "/FitNesseRoot/SuitePage/content.txt";
    public static final String STATIC_PAGE = "/FitNesseRoot/StaticPage/content.txt";

    @Before
    public void setUp() throws Exception {
        WorkspaceHelper.cleanWorkspace();
    }

    public IProject importProject(String projectName) throws Exception {
        IProject project = ProjectUtils.importProject(projectName);
        JobHelper.waitForJobsToComplete();
        return project;
    }
}
