package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import java.sql.SQLException;

public class UrlChecksController {

    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(urlId).get();

        try {
            var response = Unirest.get(url.getName()).asString();
            var statusCode = response.getStatus();
            var body = response.getBody();

            var doc = Jsoup.parse(body);
            var title = doc.title();

            var h1El = doc.selectFirst("h1");
            String h1 = h1El != null ? h1El.text() : "";

            var descriptionEl = doc.selectFirst("meta[name=description]");
            String description = descriptionEl != null ? descriptionEl.attr("content") : "";

            var check = new UrlCheck(statusCode, title, h1, description);
            check.setUrlId(urlId);
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashType", "danger");
        }

        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
