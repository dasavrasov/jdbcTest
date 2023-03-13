import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.open;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Epic("https://pobeda.aero/")
public class PobedaTests {
    MainPage page = new MainPage();

    @BeforeAll
    public static void startup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browser="chrome";
        Configuration.browserCapabilities=options;
    }

    @Test
    @Description(value = "Тест проверяет tittle и наличие логотипа")
    @Feature(value = "Группа 1 - тестирование главной страницы")
    public void test1() {
        //1.  Перейти на сайт pobeda.aero.

        open("https://pobeda.aero/");

        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        page.checkTitle("Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы");
        //б) на странице есть логотип Победы.
        page.checkLogo();
    }

    @Test
    @Description(value = "Тест проверяет блок «Управление бронированием»")
    @Feature(value = "Группа 2 - тестирование блока «Управление бронированием»")
    public void test2() {
        Configuration.browserSize="1920x1080";

        //3. Проскроллить страницу чуть ниже и кликнуть на пункт «Управление бронированием».
        page.getTicketSearch().getBooking().hover();
        page.getTicketSearch().getBooking().click();

        //4. Выбрать (или ввести) следующие критерии поиска:
        BookingSearch bookingSearch=page.getBookingSearch();

        //        а) есть поле «Номер заказа или билета»;
        bookingSearch.checkOrderNo();
//        б) есть поле «Фамилия клиента»;
        bookingSearch.checkFio();
//        в) есть кнопка «Поиск».
        bookingSearch.checkSearchBtn();

//        5. Ввести в поля ввода данные:
//        номер заказа – XXXXXX, фамилия – Qwerty
//        и нажать кнопку «Поиск».
        bookingSearch.inputOrderNo("SSSSSD");
        bookingSearch.inputFio("Qwertyyyyyyyy");
        bookingSearch.searchBtnClick();

//        6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден»
        bookingSearch.checkError("Заказ с указанными параметрами не найден");
    }

    @Test
    @Description(value = "Тест проверяет блок «Поиск билетов»")
    @Feature(value = "Группа 3 - тестирование блока «Поиск билетов»")
    public void test3() {
        TicketSearch ticketSearch=page.getTicketSearch();
        //3. Проскроллить страницу к блоку поиска билета и убедиться, что блок с поиском билета действительно отображается
        // (есть поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)
        ticketSearch.moveToFrom();

        //4. Выбрать (или ввести) следующие критерии поиска:
        //откуда – Москва (без выбора аэропорта) + нажать Enter
        //куда – Санкт-Петербург + нажать Enter.
        //текст не вводим, выбираем из списка первый элемент (Москва, Санкт-Петербург)
        ticketSearch.selectTown(ticketSearch.getFrom());
        ticketSearch.selectTown(ticketSearch.getTo());

        //6. Нажать кнопку «Поиск».
        ticketSearch.searchBtnClick();

        //7. Убедиться, что около поля «Туда» появилась красная обводка.
        //rgb(185, 0, 85) - Красный цвет
        //Должна быть ошибка
        ticketSearch.checkColor("rgb(185, 0, 80)");
    }

}
