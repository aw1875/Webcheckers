// Class Name: GetSignInRoute
// Description: This class is the UI controller to GET the Sign In page.
package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='https://github.com/axr6077'>Ayush Rout</a>
 * @author couchcoders
 * @version 1.1
 * @since 1.0
 */
public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;

    protected static final String VIEWFILE = "signin.ftl";
    protected static final String TITLE = "Sign In";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        LOG.config("GetSignInRoute Initialized.");
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute Invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", TITLE);
        return templateEngine.render(new ModelAndView(vm, VIEWFILE));
    }
}
