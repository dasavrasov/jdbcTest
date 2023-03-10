import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PikabuTests {

    WebDriver driver;

    @BeforeEach
    public void startup(){
        System.setProperty("webdriver.chrome.driver","D:/STEP-UP/UI/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(30000));

    }

    //Написано кастомное ожидание текста «Полетели в Калининград» и есть проверка, что картинка действительно отобразилась верная – для пункта 3
    public static void kalinigradImgDisplayed(WebElement element, long maxTime){
        Awaitility.with().pollDelay(Duration.ofMillis(100)).await().atMost(Duration.ofMillis(maxTime)).until(element::isDisplayed);
    }


    @Test
    public void test1(){
        //1. Открыть сайт google.com и ввести в строку поиска «Сайт компании Победа», после чего нажать Enter.
        driver.get("https://www.google.com/");

        assertEquals(driver.getCurrentUrl(),"https://www.google.com/");

        //Нажимаем кнопку "Войти"
        driver.findElement(By.cssSelector("input[title=\"Поиск\"]")).click();
        driver.findElement(By.cssSelector("input[title=\"Поиск\"]")).sendKeys("Сайт компании Победа");
        driver.findElement(By.cssSelector("input[title=\"Поиск\"]")).sendKeys((Keys.ENTER));

        //2. Дождаться прогрузки страницы с результатами поиска, после чего кликнуть на первую ссылку (https://www.pobeda.aero/).
        driver.findElement(By.xpath("//cite[contains(.,\"https://pobeda.aero\")]")).isDisplayed();
        driver.findElement(By.xpath("//cite[contains(.,\"https://pobeda.aero\")]")).click();

        //3. Дождаться прогрузки страницы АК «Победа», после чего дождаться появления картинки с текстом «Полетели в Калининград»
        // и проверить, что текст на странице действительно совпадает с текстом «Полетели в Калининград»./
        //высветилась картинка "Полетели в Калиниград"

        //Ожидание - полторы минуты
        kalinigradImgDisplayed(driver.findElement(By.cssSelector("button img[alt*=\"kaliningrad\"]")),90000);

        //4. Кликнуть на переключатель языка, выбрать английский язык
        // убедиться, что на главной странице отображаются тексты "Ticket search", "Online check-in", "Manage my booking"
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("button.dp-o0jqr4")).isDisplayed(); // нашли кнопку по классу
        driver.findElement(By.cssSelector("button.dp-o0jqr4")).click();
        driver.findElement(By.cssSelector(".dp-ic4bg9")).isDisplayed(); // нашли по классу
        driver.findElement(By.cssSelector(".dp-ic4bg9")).click();

//        выбрать английский язык
        driver.findElement(By.cssSelector("div.dp-Popover-dropdown button:nth-child(2)")).click();

//        на главной странице отображаются тексты "Ticket search", "Online check-in", "Manage my booking"
        driver.findElement(By.xpath("//button//div[contains(.,\"Ticket search\")]")).isDisplayed();
        driver.findElement(By.xpath("//button//div[contains(.,\"Online check-in\")]")).isDisplayed();
        driver.findElement(By.xpath("//button//div[contains(.,\"Manage my booking\")]")).isDisplayed();
}

    @AfterEach
    public void shutDown(){
        driver.quit();
    }

}
