package com.kotlarz.backend.web;

import com.kotlarz.frontend.ui.MainUI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
    @GetMapping("/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(MainUI.UI_PATH);
    }
}
