import io.qameta.allure.Step;
import lombok.Getter;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketSearch {
    //Откуда
    @Getter
    private SelenideElement from=$("input[placeholder=\"Откуда\"][aria-controls*=\"dropdown\"]");

    // Куда,
    @Getter
    private SelenideElement to=$("input[placeholder=\"Куда\"][aria-controls*=\"dropdown\"]");

    // Дата вылета Туда,
    @Getter
    private SelenideElement departure=$("input[id=\":Rbcualmqm:\"][placeholder=\"Туда\"]");

    // Дата вылета Обратно
    @Getter
    private SelenideElement arrival=$("input[id=\":Rrcualmqm:\"][placeholder=\"Обратно\"]");

    //Кнопка Поиск
    @Getter
    private SelenideElement searchBtn=$("button[type=\"submit\"]");

    //Управление бронированием
    @Getter
    private SelenideElement booking=$x("//div[@class='__mantine-ref-text dp-vbfp0a'][contains(.,'Управление бронированием')]");

    @Getter
    private TownList townList;

    public TicketSearch() {
        this.townList=new TownList();
    }

    @Step("Проскроллить страницу к блоку поиска билета")
    public void moveToFrom(){
        from.hover();
    }

    @Step("Выбрать город из списка")
    public void selectTown(SelenideElement element){
        element.hover();
        townList.getMenuItem().click();
    }

    @Step("Кликнуть на кнопку <Поиск>")
    public void searchBtnClick(){
        searchBtn.click();
    }

    @Step("Около поля «Туда» появилась красная обводка")
    public void checkColor(String color){
        assertEquals(departure.getCssValue("border-color"),color);
    }
}
