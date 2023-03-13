import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import static com.codeborne.selenide.Selenide.open;

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
    @Description(value = "Перейти на сайт pobeda.aero" +
            "Убедиться, что сайт открылся:" +
            "а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;" +
            "б) на странице есть логотип Победы.")
    @Feature(value = "Группа 1 - тестирование главной страницы")
    public void test1() {
        open("https://pobeda.aero/");
        page.checkTitle("Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы");
        page.checkLogo();
    }

    @Test
    @Description(value = "Проскроллить страницу чуть ниже и кликнуть на пункт «Управление бронированием»" +
            "Выбрать (или ввести) следующие критерии поиска:" +
            "а) есть поле «Номер заказа или билета»;" +
            "б) есть поле «Фамилия клиента»;" +
            "в) есть кнопка «Поиск»." +
            "Ввести в поля ввода данные:" +
            "номер заказа – XXXXXX, фамилия – Qwerty" +
            "и нажать кнопку «Поиск»" +
            "Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден»"
    )
    @Feature(value = "Группа 2 - тестирование блока «Управление бронированием»")
    public void test2() {
        Configuration.browserSize="1920x1080";

        page.getTicketSearch().getBooking().hover();
        page.getTicketSearch().getBooking().click();

        BookingSearch bookingSearch=page.getBookingSearch();

        bookingSearch.checkOrderNo();
        bookingSearch.checkFio();
        bookingSearch.checkSearchBtn();

        bookingSearch.inputOrderNo("SSSSSD");
        bookingSearch.inputFio("Qwertyyyyyyyy");
        bookingSearch.searchBtnClick();

        bookingSearch.checkError("Заказ с указанными параметрами не найден");
    }

    @Test
    @Description(value = "Проскроллить страницу к блоку поиска билета и убедиться, что блок с поиском билета действительно отображается" +
            "(есть поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)" +
            "4. Выбрать (или ввести) следующие критерии поиска:" +
            "откуда – Москва (без выбора аэропорта) + нажать Enter" +
            "куда – Санкт-Петербург + нажать Enter." +
            "текст не вводим, выбираем из списка первый элемент (Москва, Санкт-Петербург)" +
            "Нажать кнопку «Поиск»." +
            "Убедиться, что около поля «Туда» появилась красная обводка.")
    @Feature(value = "Группа 3 - тестирование блока «Поиск билетов»")
    public void test3() {
        TicketSearch ticketSearch=page.getTicketSearch();
        ticketSearch.moveToFrom();
        ticketSearch.selectTown(ticketSearch.getFrom());
        ticketSearch.selectTown(ticketSearch.getTo());
        ticketSearch.searchBtnClick();
        ticketSearch.checkColor("rgb(185, 0, 80)");
    }

}
