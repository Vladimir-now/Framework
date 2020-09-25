package ru.appline.framework.tests;

import org.junit.Test;
import ru.appline.framework.base.BaseClass;


public class MainTest extends BaseClass {

    @Test
    public void dnsShopRunTest() {
        app.getStartPage()
                .searchPlaystation() //в поиске найти playstation
                .searchPlaystation4SlimBlack() //кликнуть по playstation 4 slim black
                .writePlaystationPrice() //запомнить цену
                .chooseTwoYearWarranty() //доп.гарантия - выбрать 2 года
                .writePlaystationPriceWithWarranty() //дождаться изменения цены и запомнить цену с гарантией
                .clickBuy() //нажать Купить
                .searchDetroit() //выполнить поиск Detroit
                .writeDetroitPrice() //запомнить цену
                .clickBuy() //нажать купить
                .goToShoppingCart() //перейти в корзину
                .checkWarranty() //проверить, что для приставки выбрана гарантия на 2 года
                .checkPrice() //проверить цену каждого из товаров и сумму
                .deleteDetroit() //удалить из корзины Detroit
                .addPSPlus(2) //добавить n-количество PS
                .returnItem();


        //нажать вернуть удаленный товар, проверить что Detroit появился в корзине и сумма увеличилась на его значение



    }
}
