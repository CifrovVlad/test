package uiTest;

import Conf.config.ConfigProperties;
import core.BaseSeleniumPage;
import core.SeBaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BaseSeleniumPage {

    @FindBy(xpath = "//select[@id='id_queue']")
    public WebElement queueList;

    @FindBy(xpath = "//select[@id='id_queue']//option[@value='1']" )
    public WebElement queueValue;

    @FindBy(id = "id_title")
    public WebElement title;

    @FindBy(id = "id_body")
    public WebElement body;

    @FindBy(id = "id_due_date")
    public WebElement dateField;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']//a[text()='23']")
    public WebElement date;

    @FindBy(id = "id_submitter_email")
    public WebElement email;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement submit;


    @FindBy(id = "userDropdown")
    public WebElement userDown;

    public MainPage() {
        driver.get(ConfigProperties.URL);
        PageFactory.initElements(driver, this);

    }
    public MainPage returnToMainPage(String titleValue, String bodyValue, String emailValue) {
        queueList.click();
        queueValue.click();
        title.sendKeys(titleValue);
        body.sendKeys(bodyValue);
        dateField.click();
        date.click();
        email.sendKeys(emailValue);
        submit.click();
        return this;

    }
    public LoginPage login() {
        userDown.click();
        return new LoginPage();
    }
}
