package com.utcn.is.NiceCars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.utcn.is.NiceCars.dto.AnnouncementDto;
import com.utcn.is.NiceCars.dto.FilterDto;
import com.utcn.is.NiceCars.service.AnnouncementService;
import com.utcn.is.NiceCars.util.ImageUtil;

@Controller
public class SearchController {
	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private ImageUtil imageUtil;

	private static FilterDto filter;

	@ModelAttribute("filterDto")
	public FilterDto filterDto() {
		return new FilterDto();
	}

	@PostMapping(value = { "/", "/post", "/announcement/*", "/announcements/*", "/my-profile" }, params = "term")
	public String search(@ModelAttribute("term") String term) {
		return "redirect:/announcements/" + term;
	}

	@GetMapping("/announcements/{term}")
	public String showSearchResult(@PathVariable("term") String term, Model model) {
		model.addAttribute("announcements", announcementService.search(term));
		model.addAttribute("imageUtil", imageUtil);
		model.addAttribute("term", term);
		return "search-result";
	}

	@GetMapping("/filter")
	public String viewFilter(Model model) {
		model.addAttribute("carMakes", AnnouncementDto.CAR_MAKES);
		model.addAttribute("counties", AnnouncementDto.COUNTIES);
		model.addAttribute("fuels", AnnouncementDto.FUELS);
		model.addAttribute("bodies", AnnouncementDto.BODIES);
		model.addAttribute("gearboxes", AnnouncementDto.GEARBOXES);
		model.addAttribute("powertrains", AnnouncementDto.POWERTRAINS);
		model.addAttribute("emissions", AnnouncementDto.EMISSIONS);
		return "filter";
	}

	@PostMapping(value = "/filter", params = "filter")
	public String filter(@ModelAttribute("filterDto") FilterDto filterDto) {
		filter = filterDto;
		return "redirect:/filter/result";
	}

	@GetMapping("/filter/result")
	public String viewFilterResult(Model model) {
		model.addAttribute("announcements", announcementService.filter(filter));
		model.addAttribute("imageUtil", imageUtil);
		return "filter-result";
	}

}
