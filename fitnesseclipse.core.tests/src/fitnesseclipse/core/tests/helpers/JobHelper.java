package fitnesseclipse.core.tests.helpers;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.Assert;

public class JobHelper {

    private static final int POLLING_DELAY = 10;

    public static void waitForJobsToComplete() {
        try {
            waitForJobsToComplete(new NullProgressMonitor());
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void waitForJobsToComplete(IProgressMonitor monitor) throws InterruptedException, CoreException {
        waitForBuildJobs();

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IJobManager jobManager = Job.getJobManager();
        jobManager.suspend();
        try {
            Job[] jobs = jobManager.find(null);
            for (Job job : jobs) {
                if (job instanceof WorkspaceJob) {
                    job.join();
                }
            }
            workspace.run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) {
                }
            }, workspace.getRoot(), 0, monitor);

        } finally {
            jobManager.resume();
        }

        waitForBuildJobs();
    }

    private static void waitForBuildJobs() {
        waitForJobs(BuildJobMatcher.INSTANCE, 60 * 1000);
    }

    public static void waitForJobs(IJobMatcher matcher, int maxWaitMillis) {
        final long limit = System.currentTimeMillis() + maxWaitMillis;
        while (true) {
            Job job = getJob(matcher);
            if (job == null) {
                return;
            }
            boolean timeout = System.currentTimeMillis() > limit;
            Assert.assertFalse("Timeout while waiting for completion of job: " + job, timeout);
            job.wakeUp();
            try {
                Thread.sleep(POLLING_DELAY);
            } catch (InterruptedException e) {
                // ignore and keep waiting
            }
        }
    }

    private static Job getJob(IJobMatcher matcher) {
        Job[] jobs = Job.getJobManager().find(null);
        for (Job job : jobs) {
            if (matcher.matches(job)) {
                return job;
            }
        }
        return null;
    }

    public static interface IJobMatcher {
        boolean matches(Job job);
    }

    static class BuildJobMatcher implements IJobMatcher {

        public static final IJobMatcher INSTANCE = new BuildJobMatcher();

        @Override
        public boolean matches(Job job) {
            return (job instanceof WorkspaceJob) || job.getClass().getName().matches("(.*\\.AutoBuild.*)");
        }

    }

}