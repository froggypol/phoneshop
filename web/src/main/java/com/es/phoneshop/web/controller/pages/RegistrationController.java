package com.es.phoneshop.web.controller.pages;

import com.es.core.form.SignInUserForm;
import com.es.facade.RegistrationFacade;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Resource
    private RegistrationFacade registrationFacade;

    @PostMapping(value = "/registration")
    public String saveUserToDB(@ModelAttribute(value = "signInForm") @Valid SignInUserForm signInUserForm,
                               BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            registrationFacade.saveUser(signInUserForm);
            return "redirect:/registration?success=Success Registration!";
        }
        return "registration";
    }

    @GetMapping(value = "/registration")
    public ModelAndView getRegistrationPage(@RequestParam(name = "success", required = false) String registratedSuccessfully) {
        ModelAndView model = new ModelAndView();
        if (registratedSuccessfully != null) {
            model.addObject("msg", registratedSuccessfully);
        }
        model.setViewName("registration");
        return model;
    }

}
