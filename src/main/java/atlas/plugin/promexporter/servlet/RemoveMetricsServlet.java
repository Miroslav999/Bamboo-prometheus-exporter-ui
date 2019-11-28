package atlas.plugin.promexporter.servlet;

import io.prometheus.client.exporter.common.TextFormat;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.webresource.api.assembler.PageBuilderService;

@Scanned
public class RemoveMetricsServlet extends HttpServlet{

    private static final long serialVersionUID = -3610132083554159547L;
    

    @ComponentImport
    private final TemplateRenderer templateRenderer;
    @ComponentImport
    private PageBuilderService pageBuilderService;

    @Inject
    public RemoveMetricsServlet(TemplateRenderer templateRenderer,
            PageBuilderService pageBuilderService) {
        this.templateRenderer = templateRenderer;
        this.pageBuilderService = pageBuilderService;
    }
    
    @Override
    protected void doGet(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws IOException {

        pageBuilderService
                .assembler()
                .resources()
                .requireWebResource(
                        "prom.atlas.plugins.bamboo-prom-exporter:bootstrap-resources");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        templateRenderer.render("metrics/metrics.vm",
                httpServletResponse.getWriter());

    }

}
