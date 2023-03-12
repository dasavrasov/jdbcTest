import lombok.Getter;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

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
}
