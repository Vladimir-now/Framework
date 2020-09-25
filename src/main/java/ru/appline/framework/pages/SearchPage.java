package ru.appline.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class SearchPage extends BasePage {


    @FindBy(xpath = "//div[@class = \"catalog-item\"]//a[@class = \"ui-link\"]")
    private List<WebElement> foundItemsPlaystation;


    public ProductsPage searchPlaystation4SlimBlack () {
        for (WebElement element : foundItemsPlaystation) {
            if (element.getText().toLowerCase().contains("playstation 4 slim black".toLowerCase())) {
                element.click();
                break;
            }
        }
        return app.getProductsPage();
    }
}
