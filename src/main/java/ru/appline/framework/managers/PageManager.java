package ru.appline.framework.managers;


import ru.appline.framework.pages.*;


public class PageManager {


    private static PageManager pageManager;

    BasePage basePage;

    StartPage startPage;

    ShoppingCartPage shoppingCartPage;

    SearchPage searchPage;

    ProductsPage productsPage;


    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public BasePage getBasePage() {
        if (basePage == null) {
            basePage = new BasePage();
        }
        return basePage;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }


    public ShoppingCartPage getShoppingCartPage() {
        if (shoppingCartPage == null) {
            shoppingCartPage = new ShoppingCartPage();
        }
        return shoppingCartPage;
    }


    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }


    public ProductsPage getProductsPage() {
        if (productsPage == null) {
            productsPage = new ProductsPage();
        }
        return productsPage;
    }
}




