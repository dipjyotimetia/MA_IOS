package pageobjects;

import core.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends DriverManager {

    class PageObjects {

        @CacheLookup
        @FindBy(id = "et_username")
        public WebElement userNameFld;

        @CacheLookup
        @FindBy(id = "et_password")
        public WebElement passwordField;

        @CacheLookup
        @FindBy(id = "btnSignin")
        public WebElement loginBtn;

        @CacheLookup
        @FindBy(name = "Invalid ID or password.")
        public WebElement inputError;

        @CacheLookup
        @FindBy(id = "checkBox")
        public WebElement checkBox;

    }

    PageObjects loginPage;

    String userName = "";
    String passWord = "";

    public LoginPage() {
        super();
        loginPage = new PageObjects();
        PageFactory.initElements(driver, loginPage);
    }

    public boolean validateLoginpage(){
        boolean elements = false;
        if(loginPage.userNameFld.isDisplayed()){
            if(loginPage.passwordField.isDisplayed()){
                if(loginPage.checkBox.isDisplayed()){
                    if(loginPage.loginBtn.isDisplayed()){
                        elements = true;
                    }
                }
            }
        }
        else{
            elements = false;
        }
        return elements;
    }

    public boolean testLoginWithoutCredentials() {
        boolean loginStatus = false;
        loginPage.loginBtn.click();
        if (loginPage.inputError.getText().equalsIgnoreCase("Username is mandatory")) {
            loginStatus = true;
        }
        loginPage.userNameFld.sendKeys(userName);
        loginPage.loginBtn.click();
        if (loginPage.inputError.getText().equalsIgnoreCase("Password is mandatory")) {
            loginStatus = true;
        }
        return loginStatus;
    }


}
