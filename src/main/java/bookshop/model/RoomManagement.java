package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
			System.out.println("Raum entfernt");
			return true;
		}
		System.out.println("Raum nicht entfernt");
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
	
	public List<Room> getAllRooms()
	{
		List<Room> allRooms = new ArrayList<Room>(rooms.values());
		return allRooms;
	}
	
	public List<String> getAllRoomNames()
	{
		List<String> allNames = new ArrayList<String>();
		allNames.addAll(rooms.keySet());
		return allNames;
	}
	

}
