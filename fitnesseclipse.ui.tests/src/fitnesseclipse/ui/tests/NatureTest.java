package fitnesseclipse.ui.tests;

import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fitnesseclipse.core.tests.AbstractFitnesseTest;
import fitnesseclipse.core.tests.helpers.JobHelper;

@RunWith(SWTBotJunit4ClassRunner.class)
public class NatureTest extends AbstractFitnesseTest {

    private final SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();
        try {
            List<SWTBotView> views = bot.views(WidgetMatcherFactory.withTitle("Welcome"));
            if (!views.isEmpty()) {
                views.get(0).close();
            }
        } catch (WidgetNotFoundException e) {
        }
        bot.perspectiveByLabel("Java").activate();
    }

    @Test
    public void shouldConvertToFitnesse() throws Exception {
        IProject project = importProject(GENERAL_PROJECT);

        assertNatureNotExists(project);
        assertBuilderLength(project, 1);
        assertJavaBuilderExists(project);

        SWTBotMenu configureMenu = getConfigureMenu(GENERAL_PROJECT);
        configureMenu.menu("Convert to Fitnesse Project").click();

        try {
            configureMenu.menu("Convert to Fitnesse Project");
            fail("expected menu to not exist");
        } catch (WidgetNotFoundException e) {
        }

        JobHelper.waitForJobsToComplete();

        assertNatureExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertNatureExists(project);
        assertPageSize(3);
    }

    @Test
    public void shouldDisableFitnesse() throws Exception {
        IProject project = importProject(FITNESSE_PROJECT);

        assertNatureExists(project);
        assertBuilderLength(project, 2);
        assertJavaBuilderExists(project);
        assertNatureExists(project);

        SWTBotTreeItem packageExplorerTree = getPackageExplorerTree(FITNESSE_PROJECT).select();
        SWTBotMenu contextMenu = packageExplorerTree.contextMenu("Fitnesse");
        contextMenu.menu("Disable Fitnesse Integration").click();

        try {
            packageExplorerTree.contextMenu("Fitnesse");
            fail("expected Fitnesse menu to not exist");
        } catch (WidgetNotFoundException | TimeoutException e) {
        }

        assertNatureNotExists(project);
        assertBuilderLength(project, 1);
        assertJavaBuilderExists(project);
        assertPageSize(0);
    }

    private SWTBotMenu getConfigureMenu(String project) {
        return getPackageExplorerTree(project).select().contextMenu("Configure");
    }

    private SWTBotTreeItem getPackageExplorerTree(String project) {
        SWTBotView packageExplorerView = bot.viewByTitle("Package Explorer");
        return packageExplorerView.bot().tree().getTreeItem(project);
    }
}
