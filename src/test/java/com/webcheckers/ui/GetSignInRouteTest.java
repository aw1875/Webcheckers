// Class name: GetSignInRouteTest
// Description: This class tests for the GetSignInRoute class
package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the {@link GetSignInRoute} class
 * @author Ayush Rout
 * @version 1.1
 * @since 1.0
 */
@Tag("UI-Tier")
class GetSignInRouteTest {
    private GetSignInRoute getSignInRoute;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;

    /**
     * This method initializes the environment for testing
     */
    @BeforeEach
    void init() {
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        getSignInRoute = new GetSignInRoute(templateEngine);
    }

    /**
     * This method tests the web page loaded at sign in
     */
    @Test
    void pageLoadTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        getSignInRoute.handle(request, response);
        templateEngineTester.assertViewModelExists();
        templateEngineTester.assertViewModelIsaMap();
        templateEngineTester.assertViewModelAttribute("title", GetSignInRoute.TITLE);
        templateEngineTester.assertViewName(GetSignInRoute.VIEWFILE);
        System.out.println("Tested GetSignInRoute...");
    }
}
