package com.gpch.login.controller;

import com.gpch.login.model.User;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "이미 중복된 이메일이 존재합니다.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "회원가입에 성공하였습니다.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/modify", method = RequestMethod.GET)
    public ModelAndView modify(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("modify");
        return modelAndView;
    }

    @PutMapping(value = "/modify")
    public ModelAndView updateUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("modify");
        } else {
            userService.updateUser(user);
            modelAndView.addObject("successMessage", "회원정보 수정하였습니다.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("modify");

        }
        return modelAndView;
    }


//    회원정보 수정 실패함,,
//    @RequestMapping(value="/modify", method = RequestMethod.GET)
//    public ModelAndView modify(Authentication authentication){
//        ModelAndView modelAndView = new ModelAndView();
//        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//        modelAndView.addObject("user_id", userDetails.getUsername());
//        User user = new User();
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("modify");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
//    public ModelAndView updateUser(@Valid User user, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("modify");
//        } else {
//            userService.saveUser(user);
//            modelAndView.addObject("successMessage", "회원정보 수정하였습니다.");
//            modelAndView.addObject("user", new User());
//            modelAndView.setViewName("modify");
//        }
//        return modelAndView;
//    }

//    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
//    public ModelAndView home(){
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(auth.getName());
//        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
//        modelAndView.setViewName("admin/home");
//        return modelAndView;
//    }


}
