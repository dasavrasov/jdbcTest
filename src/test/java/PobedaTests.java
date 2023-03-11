import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.time.Duration;
import java.util.concurrent.Callable;

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
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(30000));
        actions=new Actions(driver);
    }

    @Test
    @SneakyThrows
    public void test1(){
        //1.  Перейти на сайт pobeda.aero.
        driver.get("https://pobeda.aero/");

        MainPage page = new MainPage(driver);

        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        assertEquals(page.getTitle(),"Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы");
        //б) на странице есть логотип Победы.
        page.getLogo().isDisplayed();

        //б) на странице есть логотип Победы.
        page.getLogo().isDisplayed();
        driver.manage().window().maximize();

        //3. Проскроллить страницу чуть ниже и кликнуть на пункт «Управление бронированием».
        actions.scrollToElement(page.getTicketSearch().getBooking()).perform();
        page.getTicketSearch().getBooking().click();

        //4. Выбрать (или ввести) следующие критерии поиска:
//        а) есть поле «Номер заказа или билета»;
        page.getTicketSearch().getOrderNo().isDisplayed();
//        б) есть поле «Фамилия клиента»;
        page.getTicketSearch().getFio().isDisplayed();
//        в) есть кнопка «Поиск».
        page.getTicketSearch().getSearchBtn().isDisplayed();

//        5. Ввести в поля ввода данные:
//        номер заказа – XXXXXX, фамилия – Qwerty
//        и нажать кнопку «Поиск».
        page.getTicketSearch().getOrderNo().click();
        page.getTicketSearch().getOrderNo().sendKeys("XSSSSS");
        page.getTicketSearch().getFio().click();
        page.getTicketSearch().getFio().sendKeys("Qwertyyyyyy");
        page.getTicketSearch().getSearchBtn().click();

//        6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден»
        //ждем пока откроется новая вкладка
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(numberOfWindowsToBe(2));

        //переключаемся на новую вкладку
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        //Ждем пока откроется страница с title "Просмотр заказа"
        wait.until(titleIs("Просмотр заказа"));
        //на экране отображается текст ошибки «Заказ с указанными параметрами не найден»
        page.getTicketSearch().getErrorMessage().isDisplayed();
}

    @AfterEach
    public void shutDown(){
        driver.quit();
    }

}
