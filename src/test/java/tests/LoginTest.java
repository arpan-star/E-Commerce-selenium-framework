package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce", true},
                {"invalid_user", "wrong_password", false},
                {"locked_out_user", "secret_sauce", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean isValid) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (isValid) {
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("inventory"),
                    "Valid login failed!");
        } else {
            String error = loginPage.getErrorMessage();
            Assert.assertTrue(error.length() > 0,
                    "Error message not displayed for invalid login");
        }
    }
}