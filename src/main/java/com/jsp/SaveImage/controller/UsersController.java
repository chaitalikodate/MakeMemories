package com.jsp.SaveImage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.SaveImage.Service.UsersService;
import com.jsp.SaveImage.entity.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	private UsersService userService;
	
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationPage() {
		return "register";
	}
	
	
	@PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password,HttpSession session) {
		
		Users user=new Users();
		user.setEmail(email);
		user.setPassword(password);
		user.setRoles("USER");
		boolean result= userService.login(user);
		
		ModelAndView modelAndView=new ModelAndView();
		Users userDb =userService.getUserByEmailAndPassword(user);
		if(result && userDb !=null) {
			
			session.setAttribute("userId", userDb.getId());
			 modelAndView.setViewName("redirect:/home?id=" + userDb.getId());
		}
		else {
			modelAndView.setViewName("index");
			modelAndView.addObject("error","Incorrect email or password");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/register")
    public ModelAndView register(@RequestParam("email") String email, 
                                 @RequestParam("password") String password, 
                                 @RequestParam("confirmPassword") String confirmPassword) {
		
		
		ModelAndView modelAndView = new ModelAndView();
		
		boolean status =userService.getUserByEmail(email);
		if(status) {
			System.out.println();
			modelAndView.setViewName("register");
			modelAndView.addObject("error", "Registration failed. Email may already be in use.");
			return modelAndView;
		}
		
        if (!password.equals(confirmPassword)) {
            modelAndView.setViewName("register");
            modelAndView.addObject("error", "Passwords do not match");
            return modelAndView;
        }
        
        Users user = new Users();
		user.setEmail(email);
		user.setPassword(password);
		user.setRoles("USER");
		Users userDB = userService.register(user);
		
		if(userDB!=null) {
			modelAndView.setViewName("index");
			modelAndView.addObject("success", "Registration successful! Please login.");
		} else {
			modelAndView.setViewName("register");
			modelAndView.addObject("error", "Registration failed. Email may already be in use.");
		}
		return modelAndView;
	}
	
	@PostMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate(); // Invalidate the session
        return new ModelAndView("redirect:/");
    }

}
