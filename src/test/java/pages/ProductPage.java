package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private WebDriver driver;

    By firstAddToCartBtn = By.xpath("(//button[text()='Add to cart'])[1]");
    By cartIcon = By.className("shopping_cart_link");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addFirstProductToCart() {
        driver.findElement(firstAddToCartBtn).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }
}