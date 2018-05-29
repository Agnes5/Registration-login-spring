package com.project.validator;

import com.project.model.User;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 3) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!Pattern.compile("[A-Z]+").matcher(user.getPassword()).find()) {
            errors.rejectValue("password", "CapitalLetter.userForm.password");
        }

        if (!Pattern.compile("[0-9]+").matcher(user.getPassword()).find()) {
            errors.rejectValue("password", "Number.userForm.password");
        }

        if (!Pattern.compile("\\W").matcher(user.getPassword()).find()) {
            errors.rejectValue("password", "SpecialCharacter.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "NotEmpty");
//        if (!user.getMail().contains("@\\w+\\.")) {
            if (!Pattern.compile("@\\w+\\.").matcher(user.getMail()).find()) {
            errors.rejectValue("mail", "incorrect.userForm.mail");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty");
        if (Pattern.compile("[^0-9]").matcher(user.getPhone()).find()) {
            errors.rejectValue("phone", "notNumber.userForm.phone");
        }
    }
}
