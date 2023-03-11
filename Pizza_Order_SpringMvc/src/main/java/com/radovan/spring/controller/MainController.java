package com.radovan.spring.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InvalidUserException;
import com.radovan.spring.exceptions.SuspendedUserException;
import com.radovan.spring.model.RegistrationForm;
import com.radovan.spring.service.CustomerService;

@Controller
public class MainController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelMap map) {

		return "fragments/login :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loginPassConfirm", method = RequestMethod.POST)
	public String confirmLoginPass(Principal principal) {
		Optional<Principal> authPrincipal = Optional.ofNullable(principal);
		if (!authPrincipal.isPresent()) {
			Error error = new Error("Invalid user");
			throw new InvalidUserException(error);
		}

		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loginErrorPage", method = RequestMethod.GET)
	public String loginError(ModelMap map) {
		map.put("alert", "Invalid username or password");
		return "fragments/login :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loggedout", method = RequestMethod.POST)
	public String logout(RedirectAttributes redirectAttributes) {
		SecurityContextHolder.clearContext();
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String renderRegistrationForm(ModelMap map) {
		RegistrationForm registerForm = new RegistrationForm();
		map.put("registerForm", registerForm);
		return "fragments/registrationForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String storeCustomer(@ModelAttribute("registerForm") RegistrationForm registerForm) {
		customerService.storeCustomer(registerForm);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registerComplete", method = RequestMethod.GET)
	public String registrationCompleted() {
		return "fragments/registration_completed :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registerFail", method = RequestMethod.GET)
	public String registrationFailed() {
		return "fragments/registration_failed :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/suspensionChecker", method = RequestMethod.POST)
	public String checkForSuspension() {
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (authUser.getEnabled() == (byte) 0) {
			Error error = new Error("Account suspended!");
			throw new SuspendedUserException(error);
		}

		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/suspensionPage", method = RequestMethod.GET)
	public String suspensionInterceptor(ModelMap map) {
		map.put("alert", "Account suspended!");
		return "fragments/login :: ajaxLoadedContent";
	}

}
