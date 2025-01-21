package com.jsp.SaveImage.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/homeController")
public class HomeController {

    @GetMapping
    public RedirectView home(HttpSession session) {
        // Assume "userId" is the attribute name for the user ID in session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            // Handle the case where userId is not present in the session
            // Redirect to a login page or an error page
            return new RedirectView("/login"); // Assuming there's a login page
        }

        // Construct the redirect URL with the user ID as a query parameter
        String redirectUrl = "/home?id=" + userId;
        
        // Redirect to the constructed URL
        return new RedirectView(redirectUrl);
    }
}
