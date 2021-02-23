package com.utcn.is.NiceCars.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.utcn.is.NiceCars.dto.AnnouncementDto;
import com.utcn.is.NiceCars.dto.CommentDto;
import com.utcn.is.NiceCars.dto.PhotoDto;
import com.utcn.is.NiceCars.model.Announcement;
import com.utcn.is.NiceCars.model.NotificationsCenter;
import com.utcn.is.NiceCars.service.AnnouncementService;
import com.utcn.is.NiceCars.service.UserService;
import com.utcn.is.NiceCars.util.ImageUtil;
import com.utcn.is.NiceCars.util.Mapper;

@Controller
public class AnnouncementController {
	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageUtil imageUtil;

	public static NotificationsCenter notificationsCenter = new NotificationsCenter();

	@ModelAttribute("announcement")
	public AnnouncementDto announcementDto() {
		return new AnnouncementDto();
	}

	@ModelAttribute("comment")
	public CommentDto commentDto() {
		return new CommentDto();
	}

	@ModelAttribute("photoDto")
	public PhotoDto photoDto() {
		return new PhotoDto();
	}

	@GetMapping("/")
	public String root(Model model) {
		model.addAttribute("announcements", announcementService.findAll());
		model.addAttribute("imageUtil", imageUtil);
		model.addAttribute("loggedinUser", userService.findByUsername(
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
		model.addAttribute("notificationsCenter", notificationsCenter);
		return "index";
	}

	@GetMapping("/post")
	public String showPostForm(Model model) {
		return "post";
	}

	@PostMapping(value = "/post", params = "post")
	public String postAnnouncement(@ModelAttribute("announcement") @Valid AnnouncementDto announcementDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			return "post";
		}
		Announcement announcement = announcementService.save(announcementDto,
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

		return "redirect:/announcement/" + announcement.getId() + "?success";
	}

	@GetMapping("/announcement/{id}")
	public String showAnnouncement(@PathVariable("id") Long id, Model model) {
		model.addAttribute("announcement", announcementService.findById(id).get());
		model.addAttribute("imageUtil", imageUtil);
		model.addAttribute("loggedinUser",
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("date", new Date());
		model.addAttribute("dateFormatter", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
		return "announcement";
	}

	@PostMapping(value = "/announcement/{id}", params = "post-comment")
	public String postComment(@PathVariable("id") Long id, @ModelAttribute("comment") @Valid CommentDto commentDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			return "redirect:/announcement/" + id;
		}
		announcementService.postComment(commentDto,
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),
				id);
		return "redirect:/announcement/" + id;
	}

	@PostMapping(value = "/announcement/{id}", params = "delete-comment")
	public String deleteComment(@PathVariable("id") Long id,
			@ModelAttribute("commentToDelete") String commentToDelete) {
		announcementService.deleteComment(Long.valueOf(commentToDelete));
		return "redirect:/announcement/" + id;
	}

	@GetMapping("edit-announcement/{id}")
	public String viewAnnouncementEditor(@PathVariable("id") Long id, Model model) {
		AnnouncementDto announcementDto = new AnnouncementDto();
		Announcement announcement = announcementService.findById(id).get();
		announcementDto = (AnnouncementDto) Mapper.mapAnnouncementDto(announcement, announcementDto, true);
		model.addAttribute("announcement", announcementDto);
		model.addAttribute("loggedinUser",
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("owner", announcement.getUser().getUsername());
		return "edit-announcement";
	}

	@PostMapping(value = "edit-announcement/{id}", params = "submit-changes")
	public String editAnnouncement(@PathVariable("id") Long id,
			@ModelAttribute("announcement") @Valid AnnouncementDto announcementDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			return "redirect:/edit-announcement/" + id + "?error";
		}
		announcementService.update(id, announcementDto);
		return "redirect:/announcement/" + id + "?success";
	}

	@GetMapping("promote/{id}")
	public String viewPromoteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("loggedinUser",
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("owner", announcementService.findById(id).get().getUser().getUsername());
		return "promote";
	}

	@PostMapping(value = "promote/{id}", params = "pay")
	public String promote(@PathVariable("id") Long id) {
		announcementService.promote(id);
		return "redirect:/announcement/" + id;
	}

	@GetMapping("/edit-announcement/{id}/photos")
	public String viewPhotos(@PathVariable("id") Long id, Model model) {
		model.addAttribute("photos", announcementService.findById(id).get().getPhotos());
		model.addAttribute("imageUtil", imageUtil);
		model.addAttribute("loggedinUser",
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("owner", announcementService.findById(id).get().getUser().getUsername());
		return "photos";
	}

	@PostMapping(value = "/edit-announcement/{id}/photos", params = "delete-photo")
	public String deletePhoto(@PathVariable("id") Long id, @ModelAttribute("photo_id") String photoId) {
		announcementService.deletePhoto(Long.valueOf(photoId));
		return "redirect:/edit-announcement/" + id + "/photos";
	}

	@PostMapping(value = "/edit-announcement/{id}/photos", params = "set-as-cover")
	public String changeCover(@PathVariable("id") Long id, @ModelAttribute("photoId") String photoId) {
		announcementService.changeCover(Long.valueOf(photoId));
		return "redirect:/edit-announcement/" + id + "/photos";
	}

	@PostMapping(value = "/edit-announcement/{id}/photos", params = "add-photos")
	public String addPhotos(@PathVariable("id") Long id, @ModelAttribute("photoDto") @Valid PhotoDto photoDto,
			BindingResult bindingResult) {
		if (announcementService.findById(id).get().getPhotos().size() + photoDto.getFiles().length > 10) {
			bindingResult.rejectValue("files", null,
					"The maximum number of photos for an announcement is 10. You can only add "
							+ (10 - announcementService.findById(id).get().getPhotos().size()) + " more.");
		} else {
			announcementService.addPhotos(id, photoDto);
		}

		return "redirect:/edit-announcement/" + id + "/photos";
	}

	@PostMapping(value = "/my-profile", params = "deactivate")
	public String deactivate(@ModelAttribute("toDeactivateId") String toDeactivateId) {
		announcementService.deactivate(Long.valueOf(toDeactivateId));
		return "redirect:/my-profile";
	}

	@PostMapping(value = "/my-profile", params = "activate")
	public String activate(@ModelAttribute("toActivateId") String toActivateId) {
		announcementService.activate(Long.valueOf(toActivateId));
		return "redirect:/my-profile";
	}

	@GetMapping("/statistics")
	public String viewStatistics(Model model) {
		model.addAttribute("generalMap", announcementService.getGeneralStatistics());
		model.addAttribute("makeMap", announcementService.getMakeStatistics());
		model.addAttribute("countyMap", announcementService.getCountyStatistics());
		model.addAttribute("fuelMap", announcementService.getFuelStatistics());
		model.addAttribute("bodyMap", announcementService.getBodyStatistics());
		model.addAttribute("gearboxMap", announcementService.getGearboxStatistics());
		model.addAttribute("powertrainMap", announcementService.getPowertrainStatistics());
		model.addAttribute("emissionsMap", announcementService.getEmissionsStatistics());
		return "statistics";
	}

	@PostMapping(value = "/", params = "mark-as-read")
	public String seeNotifications(@ModelAttribute("notifiedUserId") String notifiedUserId) {
		notificationsCenter.update(Long.valueOf(notifiedUserId));
		return "redirect:/";
	}

}
