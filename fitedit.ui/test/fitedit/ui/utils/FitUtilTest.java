package fitedit.ui.utils;

import org.junit.Assert;
import org.junit.Test;

import fitedit.ui.Constants;
import fitedit.ui.utils.FitUtil;

public class FitUtilTest {

    @Test
    public void testGetFitnesseUrl() {
        String root = Constants.FITNESSE_ROOT;

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
