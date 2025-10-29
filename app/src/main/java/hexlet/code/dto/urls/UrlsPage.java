package hexlet.code.dto.urls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private List<Url> urls;
}
