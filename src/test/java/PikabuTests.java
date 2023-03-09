import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PikabuTests {

    WebDriver driver;

    @BeforeEach
    public void startup(){
        System.setProperty("webdriver.chrome.driver","D:/STEP-UP/UI/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void test1(){
        driver.get("https://pikabu.ru/");

        assertEquals(driver.getCurrentUrl(),"https://pikabu.ru/");
        assertEquals(driver.getTitle(),"Горячее – самые интересные и обсуждаемые посты | Пикабу");
        //Нажимаем кнопку "Войти"
        driver.findElement(By.cssSelector(".header-right-menu__login-button")).click();

        //4. Убедиться, что отображается модальное окно «Авторизация», отображаются поля «Логин» и «Пароль», отображается кнопка «Войти».
        driver.findElement(By.cssSelector(".popup__wrapper")).isDisplayed();
        driver.findElement(By.cssSelector(".auth__notice")).isDisplayed();
        driver.findElement(By.cssSelector("div .popup__wrapper button[type=\"submit\"]")).isDisplayed();

        //отображаются поля «Логин» и «Пароль»
        driver.findElement(By.cssSelector("div .popup__wrapper input[placeholder=\"Логин\"]")).isDisplayed();
        driver.findElement(By.cssSelector("div .popup__wrapper input[placeholder=\"Пароль\"]")).isDisplayed();

        //5. Ввести в поля данные в формате логин/пароль – Qwerty/Qwerty и нажать «Войти».
        driver.findElement(By.cssSelector("div .popup__wrapper input[placeholder=\"Логин\"]")).sendKeys("Qwerty");
        driver.findElement(By.cssSelector("div .popup__wrapper input[placeholder=\"Пароль\"]")).sendKeys("Qwerty");
        driver.findElement(By.cssSelector("div .popup__wrapper button[type=\"submit\"]")).submit();

        //6.Убедиться, что появилось сообщение об ошибке, и его текст: «Ошибка. Вы ввели неверные данные авторизации».
        driver.findElement(By.xpath("//span[contains(.,\"Вы ввели неверные данные авторизации\")]")).isDisplayed();
    }

    @AfterEach
    public void shutDown(){
        driver.quit();
    }

}
