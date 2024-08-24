package uiTest;

import Conf.config.ConfigProperties;
import core.SeBaseTest;
import org.junit.Assert;
import org.junit.Test;
import uiTest.stringConstant.StringValue;

import static uiTest.stringConstant.StringModificate.getDateFormat;

public class SeleniumUiTest extends SeBaseTest {

    @Test
    public void test() {
        String title = getDateFormat(StringValue.TITLE);
    TicketPage ticketsPage = new MainPage().returnToMainPage(title, StringValue.BODY, StringValue.EMAIL)
                .login()
                .auth(ConfigProperties.NAME, ConfigProperties.PASSWORD).findTicket(title);
        Assert.assertTrue(ticketsPage.getTitle().contains(title));
        Assert.assertEquals(ticketsPage.getBody(), StringValue.BODY);
        Assert.assertEquals(ticketsPage.getEmailTicket(), StringValue.EMAIL);

    }
}
