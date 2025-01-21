package com.jsp.SaveImage.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.jsp.SaveImage.Service.ImagesService;
import com.jsp.SaveImage.Service.UsersService;
import com.jsp.SaveImage.entity.Images;
import com.jsp.SaveImage.entity.Users;
import com.jsp.SaveImage.repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AppController {
	
	@Autowired
    private ImagesService imageService;
    
    @Autowired
    private UsersService userService;
    
    @Autowired
	private UsersRepository userRepository;

    @GetMapping("/ping")
    @ResponseBody
    public String hello_world(){
        return "Hello World!";
    }

    // display image
    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Images image = imageService.viewById(id);
        if(image!=null) {
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        }
        return null;
    }
    // view All images
    @GetMapping("/home")	
    public ModelAndView home(@RequestParam("id")long userId){
        ModelAndView mv = new ModelAndView("home");
        List<Images> imageList = imageService.viewAll(userId);
        mv.addObject("imageList", imageList);
        return mv;
    }

    // add image - get
    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addimage");
    }

    // add image - post
    @PostMapping("/add")
    public String addImagePost(
            HttpServletRequest request,
            @RequestParam("image") MultipartFile file,
            @RequestParam("note") String note) throws IOException, SerialException, SQLException {
        
        // Retrieve userId from session
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        
        // Check if userId is present in the session
        if (userId == null) {
            // Handle the case where the user is not logged in or the session has expired
            return "redirect:/login"; // Redirect to login page or appropriate error handling
        }
        Users user= userRepository.findById(userId).orElse(null);
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        Images image = new Images();
        image.setImage(blob);
        image.setNote(note);
        image.setUser(user); // Assuming the Image entity has a userId field
        
        imageService.create(image);
        return "redirect:/home?id=" + userId;
    }
    
    //update -get
    @GetMapping("/update")
    public ModelAndView showUpdateForm(@RequestParam("id") long imageId, @RequestParam("userId") long userId) {
        ModelAndView mv = new ModelAndView("update");
        Images image = imageService.viewById(imageId);
        mv.addObject("image", image);
        mv.addObject("userId", userId);
        return mv;
    }
    
    //update -post
    @PostMapping("/update")
    public ModelAndView updateImage(@RequestParam("id") long imageId,
                                    @RequestParam("userId") long userId,
                                    @RequestParam("note") String note,
                                    @RequestParam("image") MultipartFile image,
                                    RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        try {
        	 Blob imageBlob = new SerialBlob(image.getBytes());

            imageService.updateImage(imageId, note, imageBlob);
            redirectAttributes.addFlashAttribute("message", "Image updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to update image: " + e.getMessage());
        }
        modelAndView.addObject("id", userId);
        return modelAndView;
    }
    
    // delete-get
    @GetMapping("/delete")
    public RedirectView deleteReq(@RequestParam("id") long imageId, 
                                  @RequestParam("userId") long userId,
                                  RedirectAttributes redirectAttributes) {
        boolean result = imageService.deleteImage(imageId);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Image deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Image not deleted!");
        }
        
        // Redirect to /home?id={userId}
        return new RedirectView("/home?id=" + userId);
    }
    
    


}
