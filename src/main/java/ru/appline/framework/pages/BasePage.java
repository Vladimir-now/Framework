package ru.appline.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.PageManager;
import ru.appline.framework.utils.PriceContainer;

import static ru.appline.framework.managers.DriverManager.getDriver;

public class BasePage {

    protected PageManager app = PageManager.getPageManager();
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 15, 500);
    protected JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//span[@class = \"cart-link__price\"]")
    protected WebElement totalPrice;

    @FindBy (xpath = "//span[@class = \"cart-link__badge\"]")
    protected WebElement amountItems;

    protected void scrollStartPage() {
        javascriptExecutor.executeScript("scroll(0, -250);");
    }

    protected WebElement waitUntilElementToBeClickable(WebElement element) {
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }
    protected WebElement waitUntilElementToBeVisible(WebElement element) {
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    protected Integer  convertStringIntoIntegerFromElement (WebElement element){
        return Integer.parseInt(waitUntilElementToBeVisible(element)
                .getText()
                .replaceAll("[^0-9]",""));
    }

    protected void waitPriceChange(Integer newPrice) {
        int count = 0;
        while(count != 10 && true ) {
            count++;
           sleep(300);
            if(PriceContainer.PLAYSTATION_PRICE != (newPrice)) break;
        }
    }
    protected int waitTotalPriceChange(Integer currentPrice, WebElement totalPrice) {
        int count = 0;
        while(count != 10 && true ) {
            count++;
            sleep(300);
            if(currentPrice != convertStringIntoIntegerFromElement(totalPrice))
            return convertStringIntoIntegerFromElement(totalPrice);
        }
        Assert.fail("Цена не изменилась, актуальное значение: " + convertStringIntoIntegerFromElement(totalPrice));
        return 0;
    }

    protected void sleep(long a) {
        try {
            Thread.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void waitAfterClick(WebElement element) {
        int countItem = Integer.parseInt(amountItems.getText());
        for (int i = 0; i < 10; i++) {
            element.click();
            sleep(1000);
            int newCountItem = Integer.parseInt(amountItems.getText());
            if(countItem != newCountItem) return;
        }
        Assert.fail("Товар не добавился!");
    }



}
