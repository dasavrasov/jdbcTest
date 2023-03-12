import lombok.Getter;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BookingSearch {
    //Номер заказа или билета
    @Getter
    private SelenideElement orderNo=$("input[placeholder=\"Номер заказа или билета\"]");
    //Фамилия клиента

    @Getter
    private SelenideElement fio=$("input[placeholder=\"Фамилия клиента\"]");
    //Кнопка Поиск

    @Getter
    private SelenideElement searchBtn=$("button[type=\"submit\"]");

    @Getter
    private SelenideElement errorMessage=$x("//div[@ng-if='vm.errorMessage']");

}
