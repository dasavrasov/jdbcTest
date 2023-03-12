import lombok.Getter;
import com.codeborne.selenide.SelenideElement;
import lombok.NoArgsConstructor;

import static com.codeborne.selenide.Selenide.*;

@NoArgsConstructor
public class TownList {
    @Getter
    private SelenideElement menuItem=$("button[class=\"dp-1h71hub\"][role=\"menuitem\"]:first-child");
}
