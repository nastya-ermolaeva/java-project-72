@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter

@param UrlsPage page

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
                <h1>Сайты</h1>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                    </thead>
                    <tbody>
                        @for (Url url : page.getUrls())
                            !{var lastCheck = page.getLastChecks().get(url.getId());}
                            <tr>
                                <td>${url.getId()}</td>
                                <td>
                                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                                </td>
                                <td>
                                    @if (lastCheck != null)
                                        ${lastCheck.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))}
                                    @endif
                                </td>
                                <td>
                                    @if (lastCheck != null)
                                        ${lastCheck.getStatusCode()}
                                    @endif
                                </td>
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
        </section>
    `
)