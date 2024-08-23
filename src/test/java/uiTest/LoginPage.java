package uiTest;

import core.BaseSeleniumPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseSeleniumPage {

    @FindBy(id = "username")
    public WebElement username;

    @FindBy(id = "password")
    public WebElement password;


    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    public TicketsPage auth(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password, Keys.ENTER);
        return new TicketsPage();
    }
}
