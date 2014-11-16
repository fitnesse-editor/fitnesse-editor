package fitnesseclipse.ui.utils;

import org.junit.Assert;
import org.junit.Test;

public class FitUtilTest {

    @Test
    public void testGetFitnesseUrl() {
        String root = "FitNesseRoot";

        Assert.assertEquals("AAA.BBB", FitUtil.getFitnesseUrl(root + "/AAA/BBB"));
        Assert.assertEquals("AAA.BBB", FitUtil.getFitnesseUrl(root + "/AAA/BBB/"));
        Assert.assertEquals("AAA.BBB", FitUtil.getFitnesseUrl(root + "/AAA/BBB/content.txt"));

        Assert.assertEquals("AAA.BBB", FitUtil.getFitnesseUrl("/XXX/YYY/" + root + "/AAA/BBB/content.txt"));

        Assert.assertEquals("AAA.BBB", FitUtil.getFitnesseUrl("XXX/YYY/" + root + "/AAA/BBB/content.txt"));

        Assert.assertEquals("", FitUtil.getFitnesseUrl(root));
        Assert.assertEquals("", FitUtil.getFitnesseUrl("/" + root + "/"));
        Assert.assertEquals("", FitUtil.getFitnesseUrl(""));
        Assert.assertEquals("", FitUtil.getFitnesseUrl("///"));
        Assert.assertNull(FitUtil.getFitnesseUrl(null));
    }
}
