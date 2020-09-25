package ru.appline.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.utils.PriceContainer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProductsPage extends BasePage {

    @FindBy(xpath = "//span[@class = 'product-card-price__current']")
    private WebElement currentPrice;

    @FindBy(xpath = "//span[@class = \"product-card-price__current product-card-price__current_active\"]")
    private WebElement currentPriceWarranty;

    @FindBy(xpath = "//select[@class=\"form-control select\"]")
    private WebElement warranty;

    @FindBy(xpath = "//button[text() = \"Купить\"]")
    private WebElement buyButton;

    @FindBy(xpath = "//a[@class=\"ui-link cart-link\"]")
    private WebElement shoppingCart;

    @FindBy(xpath = "//input[@placeholder = \\\"Поиск по сайту\\\"]\"")
    private WebElement searchDetroit;

    @FindBy(xpath = "//input[@placeholder = \"Поиск по сайту\"]")
    private WebElement selectString;


    public ProductsPage writePlaystationPrice () {
        PriceContainer.PLAYSTATION_PRICE = convertStringIntoIntegerFromElement(currentPrice);
        return this;
    }

    public ProductsPage chooseTwoYearWarranty() {
        waitUntilElementToBeClickable(warranty).click();
        Select select = new Select(warranty);
        select.selectByVisibleText("2 года");
        warranty.click();
        return this;
    }

    public ProductsPage writePlaystationPriceWithWarranty() {
        waitUntilElementToBeVisible(currentPriceWarranty);
        waitPriceChange(convertStringIntoIntegerFromElement(currentPriceWarranty));
        PriceContainer.PLAYSTATION_PRICE_WITH_WARRANTY = convertStringIntoIntegerFromElement(currentPriceWarranty);
        return this;
    }

    public ProductsPage clickBuy() {
        int totalPrice = 0;
        try {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            wait.withTimeout(Duration.ofSeconds(1));
             totalPrice = convertStringIntoIntegerFromElement(this.totalPrice);
        } catch (NoSuchElementException | TimeoutException e) {

        } finally {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait.withTimeout(Duration.ofSeconds(15));
        }

        waitUntilElementToBeClickable(buyButton).click();
        PriceContainer.TOTAL_PRICE = waitTotalPriceChange(totalPrice, this.totalPrice);
        return this;
    }

    public ProductsPage searchDetroit() {
        selectString.click();
        selectString.sendKeys("detroit");
        selectString.sendKeys(Keys.ENTER);
        return this;
    }

    public ProductsPage writeDetroitPrice() {
        waitUntilElementToBeVisible(currentPrice);
        PriceContainer.DETROIT_PRICE = convertStringIntoIntegerFromElement(currentPrice);
        return this;
    }

    private void writeTotalPrice() {
        waitUntilElementToBeVisible(totalPrice);

    }

    public ProductsPage checkPrice() {
        writeTotalPrice();
        Assert.assertEquals("Цена в корзине не соответствует сумме товаров",
                PriceContainer.TOTAL_PRICE,
                PriceContainer.PLAYSTATION_PRICE_WITH_WARRANTY
                        + PriceContainer.DETROIT_PRICE);
        return this;
    }
    public ShoppingCartPage goToShoppingCart() {
        shoppingCart.click();
        return app.getShoppingCartPage();
    }











}
