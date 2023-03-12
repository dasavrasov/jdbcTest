import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import static com.codeborne.selenide.Selenide.*;

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

    public String getTitle(){
        return $("title").getAttribute("text");
    }
}
