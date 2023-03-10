import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PobedaTests {

    WebDriver driver;
    Actions actions;

    @BeforeEach
    public void startup(){
        System.setProperty("webdriver.chrome.driver","D:/STEP-UP/UI/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(30000));
        actions=new Actions(driver);
    }

    //Написано кастомное ожидание текста «Полетели в Калининград» и есть проверка, что картинка действительно отобразилась верная – для пункта 3
    public static void kalinigradImgDisplayed(WebElement element, long maxTime){
        Awaitility.with().pollDelay(Duration.ofMillis(100)).await().atMost(Duration.ofMillis(maxTime)).until(element::isDisplayed);
    }


    @Test
    public void test1(){
        //1. Открыть сайт google.com и ввести в строку поиска «Сайт компании Победа», после чего нажать Enter.
        driver.get("https://pobeda.aero/");
        driver.manage().window().maximize();

        MainPage page = new MainPage(driver);

        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        assertEquals(page.getTitle(),"Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы");

        //б) на странице есть логотип Победы.
        page.getLogo().isDisplayed();

        //3. Проскроллить страницу к блоку поиска билета и убедиться, что блок с поиском билета действительно отображается
        // (есть поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)
        //actions.moveToElement(page.getInfo()).perform();
        actions.scrollToElement(page.getTicketSearch().getFrom()).perform();

        //4. Выбрать (или ввести) следующие критерии поиска:
        //откуда – Москва (без выбора аэропорта) + нажать Enter
        //куда – Санкт-Петербург + нажать Enter.
        //текст не вводим, выбираем из списка первый элемент (Москва, Санкт-Петербург)
        page.getTicketSearch().getFrom().click();
        page.getTicketSearch().getFrom().click();
        page.getTicketSearch().getTownList().getMenuItem().click();
        page.getTicketSearch().getTo().click();
        page.getTicketSearch().getTownList().getMenuItem().click();

        //6. Нажать кнопку «Поиск».
        page.getTicketSearch().getSearchBtn().click();

        //7. Убедиться, что около поля «Туда» появилась красная обводка.
        //rgb(185, 0, 85) - Красный цвет
        assertEquals(page.getTicketSearch().getDeparture().getCssValue("border-color"),"rgb(185, 0, 85)");

}

    @AfterEach
    public void shutDown(){
        driver.quit();
    }

}
