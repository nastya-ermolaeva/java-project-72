package hexlet.code.dto.urls;

import lombok.AllArgsConstructor;
import lombok.Getter;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Url url;
}
