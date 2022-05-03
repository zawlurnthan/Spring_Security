package com.zthan.spring_Security.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            String text;

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                text = "The page you are looking for cannot be found";
            }
            else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                text = "You are not authorize to the page you are requesting.";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                text = "You don't have permissions for this page.";
            }
            else {
                text = "Something went wrong.";
            }
            model.addAttribute("errorText", text);
            model.addAttribute("errorCode", statusCode);
        }
        else {
            model.addAttribute("errorText", "An unknown error has occurred.");
            model.addAttribute("errorCode", "Unknown");
        }
        return "error";
    }
}
