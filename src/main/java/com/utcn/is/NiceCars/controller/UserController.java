package com.utcn.is.NiceCars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.utcn.is.NiceCars.dto.UserRegistrationDto;
import com.utcn.is.NiceCars.model.User;
import com.utcn.is.NiceCars.service.AnnouncementService;
import com.utcn.is.NiceCars.service.UserService;
import com.utcn.is.NiceCars.util.ImageUtil;
import com.utcn.is.NiceCars.util.Mapper;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private ImageUtil imageUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AnnouncementService announcementService;

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		return "registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userRegistrationDto,
			BindingResult bindingResult) {

		User existing = userService.findByEmail(userRegistrationDto.getEmail());
		if (existing != null) {
			bindingResult.rejectValue("email", null, "There is already an account registered with that email");
		}
		existing = userService.findByUsername(userRegistrationDto.getUsername());
		if (existing != null) {
			bindingResult.rejectValue("username", null, "There is already an account registered with that username");
		}

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userRegistrationDto);
		return "redirect:/registration?success";
	}

	@GetMapping("/my-profile")
	public String showProfile(Model model) {
		User principal = userService.findByUsername(
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("currentUser", principal);
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		userRegistrationDto = (UserRegistrationDto) Mapper.mapUserRegistrationDto(principal, userRegistrationDto,
				passwordEncoder, true);
		model.addAttribute("userDto", userRegistrationDto);
		model.addAttribute("imageUtil", imageUtil);
		return "profile";
	}

	@PostMapping(value = "/my-profile", params = "delete")
	public String delete(@ModelAttribute("ann") String announcementId) {
		userService.deleteAnnouncement(announcementService.findById(Long.valueOf(announcementId)).get());
		return "redirect:/my-profile";
	}

	@PostMapping(value = "/my-profile", params = "update-info")
	public String update(@ModelAttribute("userDto") @Valid UserRegistrationDto userRegistrationDto,
			BindingResult bindingResult) {

		User existing = userService.findByEmail(userRegistrationDto.getEmail());

		if (existing != null && !existing.getUsername().equals(
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())) {
			bindingResult.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (bindingResult.hasErrors()) {
			return "redirect:/my-profile";
		}

		userService.update(userService.findByUsername(
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.getId(), userRegistrationDto);
		return "redirect:/my-profile";
	}

}
