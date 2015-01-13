package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class RoomForm {
	
	@NotEmpty(message = "{RoomFrom.roomNumber.NotEmpty}")
	private String roomNumber;
	
	@NotEmpty(message = "{RoomFrom.roomname.NotEmpty}")
	@Pattern(regexp="(^[A-Z]{1}.*)", message ="{RoomForm.roomName.capitalbeginning}")
	private String roomName;
	
	@NotEmpty(message="{RoomForm.chairNumber.NotEmpty}")
	@Pattern(regexp="([1-9][0-9]*$)")
	private String chairNum;
	
	
	public String getRoomNumber() {
		return this.roomNumber;
	}
	
	public void setRoomNumber(String number) {
		this.roomNumber = number;
	}
	
	public String getRoomName() {
		return this.roomName;
	}
	
	public void setRoomName(String name) {
		this.roomName = name;
	}
	
	public String getChairNum()
	{
		return this.chairNum;
	}
	
	public void setChairNum(String chairs)
	{
		this.chairNum = chairs;
	}
	
}
