package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.util.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JtelistGenerated {
	public static final String JTE_NAME = "urls/list.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,3,5,5,7,7,8,8,9,9,9,9,10,10,10,13,13,29,29,31,31,31,33,33,33,33,33,33,33,33,33,33,33,33,38,38,43,43,43,43,43,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n            <div class=\"rounded-0 m-0 alert alert-");
					jteOutput.setContext("div", "class");
					jteOutput.writeUserContent(page.getFlashType());
					jteOutput.setContext("div", null);
					jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\r\n                <p class=\"m-0\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("</p>\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
				}
				jteOutput.writeContent("\r\n\r\n        <section>\r\n            <div class=\"container-lg mt-5\">\r\n                <h1>Сайты</h1>\r\n\r\n                <table class=\"table table-bordered table-hover mt-3\">\r\n                    <thead>\r\n                    <tr>\r\n                        <th class=\"col-1\">ID</th>\r\n                        <th>Имя</th>\r\n                        <th class=\"col-2\">Последняя проверка</th>\r\n                        <th class=\"col-1\">Код ответа</th>\r\n                    </tr>\r\n                    </thead>\r\n                    <tbody>\r\n                        ");
				for (Url url : page.getUrls()) {
					jteOutput.writeContent("\r\n                            <tr>\r\n                                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\r\n                                <td>\r\n                                    <a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\r\n                                </td>\r\n                                <td></td>\r\n                                <td></td>\r\n                            </tr>\r\n                        ");
				}
				jteOutput.writeContent("\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n        </section>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
