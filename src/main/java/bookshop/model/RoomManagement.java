package bookshop.model;

import java.util.HashMap;
import java.util.Map;

public class RoomManagement {
	private static RoomManagement instance;
	private Map<String, Room> rooms = new HashMap<String , Room>();
	
	private RoomManagement()
	{
		
	}
	
	public static synchronized RoomManagement getInstance()
	{
		if(instance == null)
		{
			instance = new RoomManagement();
		}
		return RoomManagement.instance;
	}
	
	public boolean addRoom(String name , String number)
	{
		if(!rooms.containsKey(name))
		{
			rooms.put(name, new Room(name, number));
			return true;
		}
		return false;
	}
	
	public boolean removeRoom(String name)
	{
		return false;
	}
	
	public boolean changeRoom(String name)
	{
		return false;
	}
	
	public Room getRoom(String name) 
	{
		
		if(rooms.containsKey(name))
		{
			return rooms.get(name);
		}
		return null;
	}
	

}
