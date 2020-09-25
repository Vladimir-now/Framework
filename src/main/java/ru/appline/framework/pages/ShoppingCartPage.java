package ru.appline.framework.pages;

import org.junit.Assert;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.utils.PriceContainer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShoppingCartPage extends BasePage {


    @FindBy(xpath = "//a[contains(text(), \"PlayStation 4 Slim Black\")]")
    private WebElement playstationInCart;

    @FindBy(xpath = "//div[contains(@data-commerce-target, \"24\")]/span[contains(@class, \"icon_checked\")]")
    private WebElement twoYearWarranty;

    @FindBy(xpath = "//div[@class=\"cart-items__product\"]//span[@class=\"price__current\"]")
    private List<WebElement> pricesItemsInShopCart;

    @FindBy(xpath = "//span[contains(@class,\"icon_checked\")]/../../span")
    private WebElement warrantyInShopCart;

    @FindBy(xpath = "//a[contains(text(), \"Detroit\")]/../..//button[text() = \"Удалить\"]")
    private WebElement deleteDetroitButton;

    @FindBy(xpath = "//a[contains(text(), \"Detroit\")]")
    private WebElement detroitItem;

    @FindBy(xpath = "//div[@class=\"cart-items__product\"]//a[contains(@class, \"name-link\")]")
    private List<WebElement> itemsInShopCart;

    @FindBy (xpath = "//span[@class = \"cart-link__price\"]")
    private WebElement newTotalPrice;

    @FindBy (xpath = "//i[@class=\"count-buttons__icon-plus\"]")
    private WebElement plusButton;

    @FindBy(xpath = "//span[@class=\"restore-last-removed\"]")
    private WebElement returnItemButton;

    public ShoppingCartPage checkWarranty() {
        if (playstationInCart.isDisplayed()) {
            Assert.assertEquals("Выбрана не та гарантия", true, twoYearWarranty.isEnabled());
        } else {
            Assert.fail("Приставки нет в корзине");
        }
        return this;
    }

    public ShoppingCartPage checkPrice() {

        Assert.assertEquals("Неверная цена у Playstation", PriceContainer.PLAYSTATION_PRICE, (int) convertStringIntoIntegerFromElement(pricesItemsInShopCart.get(0)));
        Assert.assertEquals("Неверная цена у Detroit", PriceContainer.DETROIT_PRICE, (int) convertStringIntoIntegerFromElement(pricesItemsInShopCart.get(1)));

        int sum = 0;
        for (WebElement element: pricesItemsInShopCart) {
            sum += convertStringIntoIntegerFromElement(element);
        }
        sum += convertStringIntoIntegerFromElement(warrantyInShopCart);
        Assert.assertEquals("Неверная общая сумма", PriceContainer.TOTAL_PRICE, sum);
        return this;
    }

    public ShoppingCartPage deleteDetroit() {
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

        waitUntilElementToBeClickable(deleteDetroitButton).click();
        waitTotalPriceChange(totalPrice, this.totalPrice);
        scrollStartPage();
        return this;
    }

    private void clickPlus() {
      waitAfterClick(plusButton);
    }

    public ShoppingCartPage addPSPlus (int n) {
        int totalPrice = convertStringIntoIntegerFromElement(this.totalPrice);
        for (int i = 0; i < n; i++) {
            clickPlus();
        }
        int currentTotalPrice = waitTotalPriceChange(totalPrice, this.totalPrice);
        Assert.assertEquals("Цена в корзине не равна сумме товаров", PriceContainer.PLAYSTATION_PRICE_WITH_WARRANTY * (n+1), currentTotalPrice);
        return this;
    }

    public void returnItem () {
        int currentTotalPrice = convertStringIntoIntegerFromElement(totalPrice);
        waitAfterClick(returnItemButton);
        for (WebElement e : itemsInShopCart) {
            if (e.getText().startsWith("Игра Detroit")) {
                Assert.assertEquals("Товар не вернулся", currentTotalPrice + PriceContainer.DETROIT_PRICE, (int) convertStringIntoIntegerFromElement(totalPrice));
            }
        }
    }
}
