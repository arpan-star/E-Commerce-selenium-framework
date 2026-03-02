package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;
import pages.ProductPage;

public class AddToCartTest extends BaseTest {
	
	
	@Test
	public void addProductToCartTest() {
		LoginPage login = new LoginPage(driver);
		test.info("Launching application");
		
		test.info("Logging in with valid credentials");
		login.login("standard_user", "secret_sauce");
		
		ProductPage productpage = new ProductPage(driver);
		
		test.info("Adding product to cart");
		productpage.addFirstProductToCart();
		productpage.goToCart();
		//Assert.assertTrue(false);
		
		
	}
	
	
}
