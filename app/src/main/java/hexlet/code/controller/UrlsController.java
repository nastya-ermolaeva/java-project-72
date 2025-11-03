package hexlet.code.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import java.sql.SQLException;
import java.net.URI;
import java.util.Map;
import java.util.HashMap;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;

public class UrlsController {
    // Главная страница с формой ввода (GET /)
    public static void index(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("index.jte", model("page", page));
    }

    // Добавляем новый URL (POST /urls)
    public static void create(Context ctx) throws SQLException {
        var rawUrl = ctx.formParam("url");

        String normalizedUrl;
        try {
            normalizedUrl = normalizeUrl(rawUrl);
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlRepository.existsByName(normalizedUrl)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");
            ctx.redirect(NamedRoutes.urlsPath());
            return;
        }

        var url = new Url(normalizedUrl);
        UrlRepository.save(url);
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.urlsPath());
    }

    // Страница со всеми сайтами (GET /urls)
    public static void list(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        Map<Long, UrlCheck> lastChecks = new HashMap<>();

        for (var url : urls) {
            var lastCheck = UrlCheckRepository.findLastCheck(url.getId());
            if (lastCheck.isPresent()) {
                lastChecks.put(url.getId(), lastCheck.get());
            }
        }
        var page = new UrlsPage(urls, lastChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/list.jte", model("page", page));
    }

    // Страница конкретного сайта (GET /urls/{id})
    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL с id №" + id + " не найден"));
        var checks = UrlCheckRepository.getAllChecks(id);
        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/show.jte", model("page", page));
    }


    private static String normalizeUrl(String input) throws Exception {
        var uri = new URI(input);
        var url = uri.toURL();

        var protocol = url.getProtocol();
        var host = url.getHost();
        var port = url.getPort();

        var normalizedUrl = new StringBuilder(protocol)
                .append("://")
                .append(host);

        if (port != -1) {
            normalizedUrl.append(":").append(port);
        }

        return normalizedUrl.toString();
    }
}
