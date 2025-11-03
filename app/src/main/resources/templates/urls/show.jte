@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlPage page

@template.layout.page(
    content = @`
        @if (page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-${page.getFlashType()} alert-dismissible fade show" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <section>
            <div class="container-lg mt-5">
                <h1>Сайт: ${page.getUrl().getName()}</h1>

                <table class="table table-bordered table-hover mt-3">
                    <tbody>
                        <tr>
                            <td>ID</td>
                            <td>${page.getUrl().getId()}</td>
                        </tr>
                        <tr>
                            <td>Имя</td>
                            <td>${page.getUrl().getName()}</td>
                        </tr>
                        <tr>
                            <td>Дата создания</td>
                            <td>${page.getUrl().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))}</td>
                        </tr>
                    </tbody>
                </table>

                <h2 class="mt-5">Проверки</h2>
                <form method="post" action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}">
                    <button type="submit" class="btn btn-primary">Запустить проверку</button>
                </form>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th class="col-1">ID</th>
                            <th class="col-1">Код ответа</th>
                            <th>title</th>
                            <th>h1</th>
                            <th>description</th>
                            <th class="col-2">Дата проверки</th>
                        </tr>
                    </thead>
                    <tbody>
                        @for (UrlCheck check : page.getChecks())
                            <tr>
                                <td>${check.getId()}</td>
                                <td>${check.getStatusCode()}</td>
                                <td>${check.getTitle()}</td>
                                <td>${check.getH1()}</td>
                                <td>${check.getDescription()}</td>
                                <td>${check.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))}</td>
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
        </section>
    `
)