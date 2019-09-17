package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostSignInRouteTest {
    private PostSignInRoute postSignInRoute;
    private PlayerLobby playerLobby;
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    @BeforeEach
    void init() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        postSignInRoute = new PostSignInRoute(playerLobby, templateEngine);
    }

    @Test
    void invalidUsernameFailTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        when(request.queryParams("username")).thenReturn("P1@yeR)");
        postSignInRoute.handle(request, response);
        templateEngineTester.assertViewModelExists();
        templateEngineTester.assertViewModelIsaMap();
        templateEngineTester.assertViewModelAttribute(GetHomeRoute.TITLE_KEY, PostSignInRoute.TITLE);
    }

    @Test
    void longUsernameTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        when(request.queryParams("username")).thenReturn("AyushWooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooot");
        postSignInRoute.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Please choose your username less than 25 characters");
    }

    @Test
    void usernameTakenTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        playerLobby.addPlayer(new Player("Ayush"));
        when(request.queryParams("username")).thenReturn("Ayush");
        postSignInRoute.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Username already exists");
    }

    @Test
    void validUsernameTest() {
        when(request.queryParams("username")).thenReturn("Ayush");
        postSignInRoute.handle(request, response);
        assertTrue(playerLobby.userNameExists("Ayush"));
    }

    @Test
    void nullUsernameTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        when(request.queryParams("username")).thenReturn("");
        postSignInRoute.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PostSignInRoute.FAIL_KEY, "Please enter a valid username");
    }
}
