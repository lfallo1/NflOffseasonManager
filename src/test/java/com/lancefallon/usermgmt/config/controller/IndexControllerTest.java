package com.lancefallon.usermgmt.config.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class IndexControllerTest {

    IndexController indexController;

    @BeforeEach
    void setUp(){
        indexController = new IndexController();
    }

    @Test
    @DisplayName("Test Index Controller::getIndex")
    void getIndex() {
        assertAll("Text index controller responses",
                () -> assertEquals("index.html", indexController.getIndex()),
                () -> assertNotEquals("oops", indexController.getIndex()));
    }

    @Test
    @DisplayName("Test page not found")
    void pageNotFound() throws IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).sendRedirect(Mockito.anyString());

        indexController.pageNotFound(response);
        Mockito.verify(response, times(1)).sendRedirect(Mockito.anyString());
    }
}