package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public boolean addRoom(String name , String number, String chairNum)
	{
		if(!rooms.containsKey(name))
		{
			Iterator<Room> roomite = rooms.values().iterator();
			while(roomite.hasNext())
			{
				Room eqRoom = roomite.next();
				if(eqRoom.getNumber().equals(number))
				{
					return false;
				}
			}
			
			int chairs = Integer.parseInt(chairNum);
			rooms.put(name, new Room(name, number, chairs));
			
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the Room with the given name from the list
	 * @param name
	 * @return
	 */
	public boolean removeRoom(String name)
	{
		if(rooms.containsKey(name))
		{
			rooms.remove(name);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Edits the Room with the given name
	 * @param name
	 * @return
	 */
	public boolean changeRoom(String name)
	{
		return false;
	}
	
	
	/**
	 * @param userAccountManager
	 * @param userRepository
	 */
	public Room getRoom(String name) 
	{
		
		if(rooms.containsKey(name))
		{
			return rooms.get(name);
		}
		return null;
	}
	
	public ArrayList<Room> getAllRooms()
	{
		return new ArrayList<Room>(rooms.values());
	}
	

}
