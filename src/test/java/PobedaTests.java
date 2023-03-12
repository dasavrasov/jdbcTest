import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PobedaTests {

    @BeforeAll
    public static void startup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browser="chrome";
        Configuration.browserCapabilities=options;
    }

    @Test
    public void test1(){
        //1.  Перейти на сайт pobeda.aero.

        open("https://pobeda.aero/");

        MainPage page = new MainPage();
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        assertEquals(page.getTitle(),"Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы");
        //б) на странице есть логотип Победы.
        page.getLogo().shouldBe(visible);

        //б) на странице есть логотип Победы.
        page.getLogo().shouldBe(visible);
        Configuration.browserSize="1920x1080";

        //3. Проскроллить страницу чуть ниже и кликнуть на пункт «Управление бронированием».
        page.getTicketSearch().getBooking().hover();
        page.getTicketSearch().getBooking().click();

        //4. Выбрать (или ввести) следующие критерии поиска:
//        а) есть поле «Номер заказа или билета»;
        page.getBookingSearch().getOrderNo().shouldBe(visible);
//        б) есть поле «Фамилия клиента»;
        page.getBookingSearch().getFio().shouldBe(visible);
//        в) есть кнопка «Поиск».
        page.getBookingSearch().getSearchBtn().shouldBe(visible);

//        5. Ввести в поля ввода данные:
//        номер заказа – XXXXXX, фамилия – Qwerty
//        и нажать кнопку «Поиск».
        page.getBookingSearch().getOrderNo().setValue("SSSSSD");
        page.getBookingSearch().getFio().setValue("Qwertyyyyyyyy");
        page.getBookingSearch().getSearchBtn().click();

//        6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден»
        //ждем пока откроется новая вкладка
        switchTo().window(1,Duration.ofSeconds(30));
        //Ждем пока откроется страница с title "Просмотр заказа"
        //на экране отображается текст ошибки «Заказ с указанными параметрами не найден»
        page.getBookingSearch().getErrorMessage().shouldHave(exactText("Заказ с указанными параметрами не найден"));
}

}
