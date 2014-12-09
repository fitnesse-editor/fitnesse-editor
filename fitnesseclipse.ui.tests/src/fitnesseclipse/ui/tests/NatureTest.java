package fitnesseclipse.ui.tests;

import static org.junit.Assert.fail;
import org.eclipse.core.resources.IProject;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import fitnesseclipse.core.tests.AbstractFitnesseTest;
import fitnesseclipse.core.tests.helpers.JobHelper;

@RunWith(SWTBotJunit4ClassRunner.class)
public class NatureTest extends AbstractFitnesseTest {

    private SWTWorkbenchBot bot = new SWTWorkbenchBot();

    @Before
    public void setup() {
        try {
            bot.viewByTitle("Welcome").close();
        } catch (WidgetNotFoundException e) {
        }

        // open "Open Perspective" dialog
        SWTBotMenu windowMenu = bot.menu("Window");
        windowMenu.click();
        SWTBotMenu perspectiveMenu = windowMenu.menu("Open Perspective");
        perspectiveMenu.click();
        SWTBotMenu otherMenu = windowMenu.menu("Other...");
        otherMenu.click();

        // select "LDAP" perspective
        SWTBotTable table = bot.table();
        table.select("Java");

        // press "OK"
        SWTBotButton okButton = bot.button("OK");
        okButton.click();

        // wait till Connections view become visible
        bot.waitUntil(new DefaultCondition() {
            @Override
            public boolean test() throws Exception {
                return NatureTest.this.bot.perspectiveByLabel("Java") != null;
            }

            @Override
            public String getFailureMessage() {
                return "Could not find widget";
            }
        });
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
        contextMenu.menu("Disable Fitnesse Nature").click();

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
