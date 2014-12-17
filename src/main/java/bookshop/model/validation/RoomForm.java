package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class RoomForm {
	
	@NotEmpty(message = "{RoomFrom.roomNumber.NotEmpty}")
	private String roomNumber;
	
	@NotEmpty(message = "{RoomFrom.roomname.NotEmpty}")
	@Pattern(regexp="(^[A-Z]{1}.*)", message ="{RoomForm.roomName.capitalbeginning}")
	private String roomName;
	
	public String getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(String number) {
		this.roomNumber = number;
	}
	
	public String getRoomName() {
		return roomNumber;
	}
	
	public void setRoomName(String name) {
		this.roomNumber = name;
	}
	
}
