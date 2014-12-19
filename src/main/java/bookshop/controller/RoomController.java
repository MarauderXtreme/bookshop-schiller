package bookshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.RoomManagement;

@Controller
public class RoomController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/room/new", method = RequestMethod.POST)
	public String add(@RequestParam("rName") String rName, @RequestParam("rNum") String rNum, @RequestParam("rChairs") String chairs)
	{
		RoomManagement.getInstance().addRoom(rName, rNum, chairs);
		return "redirect:/admin/room/add";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/room/add")
	public String rooms(String roomNumber, String roomName,String chairs, Model model) {

		model.addAttribute("roomname", roomName);
		model.addAttribute("roomNumber", roomNumber);
		model.addAttribute("chairNumber", chairs);
		model.addAttribute("roomList" , RoomManagement.getInstance().getAllRooms());

		return "addroom";
	}
	
}
