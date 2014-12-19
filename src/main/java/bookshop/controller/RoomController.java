package bookshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.RoomManagement;

@Controller
public class RoomController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/room/new", method = RequestMethod.POST)
	public String add(@RequestParam("rName") String rName, @RequestParam("rNum") String rNum, @RequestParam("rChairs") String chairs, BindingResult result)
	{
		if(!RoomManagement.getInstance().addRoom(rName, rNum, chairs))
		{
			result.addError(new ObjectError("Room", "Der eingegebene Raumname oder die eingegebene Raumnummer ist bereits vergeben"));
		}
		
		if(result.hasErrors())
		{
			
		}
		return "redirect:/admin/room/add";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/room/add")
	public String rooms( Model model) {

		model.addAttribute("roomList" , RoomManagement.getInstance().getAllRooms());

		return "addroom";
	}
	
}
