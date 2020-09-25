package ru.appline.framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class StartPage extends BasePage {

    @FindBy(xpath = "//input[@placeholder = \"Поиск по сайту\"]")
    private WebElement selectString;


    private void selectSearchString() {
        selectString.click();
    }

    public SearchPage searchPlaystation() {
        selectSearchString();
        selectString.sendKeys("playstation");
        selectString.sendKeys(Keys.ENTER);
        return app.getSearchPage();
    }

}
