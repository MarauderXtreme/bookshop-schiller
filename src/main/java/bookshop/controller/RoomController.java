package bookshop.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.RoomManagement;
import bookshop.model.validation.RoomForm;
/**
 * 
 * @author Maximilian
 *
 */
@Controller
public class RoomController {

	/**
	 * adds a room to the roomlist
	 * @param rName
	 * @param rNum
	 * @param chairs
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/room/new", method = RequestMethod.POST)
	public String add(@RequestParam("roomName") String rName, @RequestParam("roomNumber") String rNum, @RequestParam("chairNum") String chairs, @ModelAttribute("roomForm") @Valid RoomForm roomForm,BindingResult result)
	{
		if(Integer.parseInt(chairs)<1)
		{
			result.addError(new ObjectError("RoomForm.chairNum", "No negative chairnumbers allowed! Must be a Number!"));
		}
		if(result.hasErrors())
		{
			System.out.println(result.getAllErrors());
			return "redirect:/admin/room/add";
		}
		RoomManagement.getInstance().addRoom(rName, rNum, chairs);
		return "redirect:/admin/room/add";
	}
	
	/**
	 * interface for adding rooms
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/room/add")
	public String rooms(Model model,@ModelAttribute("roomForm") @Valid RoomForm roomForm,BindingResult result)
	{
		model.addAttribute("roomForm", new RoomForm());
		model.addAttribute("roomList" , RoomManagement.getInstance().getAllRooms());
		return "addroom";
	}
	
	/**
	 * removes a room from the roomlist
	 * @param model
	 * @param rName
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value= "/admin/room/remove", method=RequestMethod.POST)
	public String removeRoom(Model model, @RequestParam("deleteRoom")String rName)
	{
		RoomManagement.getInstance().removeRoom(rName);
		model.addAttribute("roomList" , RoomManagement.getInstance().getAllRooms());
		return"redirect:/admin/room/add";
	}
	
}
