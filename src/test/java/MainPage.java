import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {

    @Getter
    private SelenideElement logo=$("div a img[src*=\"logo\"]");

    @Getter
    private SelenideElement info=$x("//div[contains(@class,'dp-Group-root')]/a[contains(@href,'information')]/div");

    @Getter
    private SelenideElement flightInfo=$x("//div[contains(.,'Подготовка к полёту')]");

    @Getter
    private SelenideElement infoUseful=$x("//div[contains(.,'Полезная информация')]");

    @Getter
    private SelenideElement about=$x("//div[contains(.,'О компании')]");

    @Getter
    private TicketSearch ticketSearch;
    @Getter
    private BookingSearch bookingSearch;

    public MainPage() {
        this.ticketSearch=new TicketSearch();
        this.bookingSearch=new BookingSearch();
    }

    @Step("Проверка tittle = {tittle}")
    public void checkTitle(String tittle){
        assertEquals($("title").getAttribute("text"), tittle);
    }
    @Step("Проверка logo")
    public void checkLogo(){
        logo.shouldBe(visible);
    }
}
