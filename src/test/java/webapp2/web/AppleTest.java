package webapp2.web;

import core.BaseTest;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import Conf.config.ConfigProperties;

public class AppleTest extends BaseTest {

    private static final String URL = "http://appleinsider.ru";
    private static final String TEXT = "Чем Iphone 13 отличается от Iphone 12";
    private static final String SEARCH = "iphone-13";



    @Test
    public void testApple() {
        Assert.assertTrue(new MainPage(URL).serchElement(TEXT).searchHref().contains(SEARCH));

    }
}
