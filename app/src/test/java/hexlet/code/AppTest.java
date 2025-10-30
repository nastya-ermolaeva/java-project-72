package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {

    private Javalin app;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
        UrlRepository.delete();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Сайты");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);

            var listResponse = client.get("/urls");
            var body = listResponse.body().string();
            assertThat(body).contains("www.example.com");
        });
    }

    @Test
    public void testCreateDuplicatedUrl() throws SQLException {
        var url = new Url("http://www.ozon.ru");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=http://www.ozon.ru";
            client.post("/urls", requestBody);

            var urls = UrlRepository.getEntities();
            assertThat(urls.size()).isEqualTo(1);
        });
    }

    @Test
    public void testShowUrlPage() throws SQLException {
        var url = new Url("https://www.youtube.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("www.youtube.com");
        });
    }

    @Test
    void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=notAvalid-url";
            client.post("/urls", requestBody);

            var urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }
}
