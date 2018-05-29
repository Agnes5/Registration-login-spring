package com.project.web;

import com.project.model.User;
import com.project.service.SecurityService;
import com.project.service.UserService;
import com.project.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @RequestBody User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResult.toString();
        }

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "Successfully registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @RequestBody User userForm) {
        User user = userService.findByUsername(userForm.getUsername());
        if (user == null) {

            return "invalid username";
        }
        if (bCryptPasswordEncoder.matches(userForm.getPassword(), user.getPassword())) {
            return "successfully login";
        }
        return "invalid password";
    }

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public String mail(@Valid @RequestBody User userForm) {
        userService.updateMail(userForm.getUsername(), userForm.getMail());
        return "successfully";
    }

    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public String phone(@Valid @RequestBody User userForm) {
        userService.updatePhone(userForm.getUsername(), userForm.getPhone());
        return "successfully";
    }


}
