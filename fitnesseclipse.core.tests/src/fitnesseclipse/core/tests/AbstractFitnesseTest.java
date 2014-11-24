package fitnesseclipse.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.Before;

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
        ProjectUtils.deleteAllProjects();
    }

    public IProject importProject(String projectName) throws Exception {
        try {
            return ProjectUtils.importProject(projectName);
        } finally {
            IJobManager jobManager = Job.getJobManager();
            jobManager.suspend();
            Job[] find = jobManager.find(null);
            for (Job job : find) {
                if (job instanceof WorkspaceJob) {
                    job.join();
                }
            }
            jobManager.resume();
        }
    }
}
