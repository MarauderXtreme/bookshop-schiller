package bookshop.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.RoomManagement;
import bookshop.model.validation.RoomForm;

@Controller
public class RoomController {
	
	public RoomController() {}
	
	@RequestMapping("/admin/room/add")
	public String room(@ModelAttribute("profileForm") @Valid RoomForm roomForm, BindingResult result, ModelMap modelMap) {
		
		RoomManagement.getInstance();
		
		return "addroom";

	}
	
	@RequestMapping("/admin/room/new")
	public String addRoom(@ModelAttribute("profileForm") @Valid RoomForm roomForm, BindingResult result, ModelMap modelMap) {
		
		RoomManagement.getInstance();
		
		return "addroom";

	}
	
}
