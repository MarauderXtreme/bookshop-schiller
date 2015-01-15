package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Maximilian
 *
 */
public class RoomManagement {
	
	private static RoomManagement instance;
	private Map<String, Room> rooms = new HashMap<String , Room>();
	
	/**
	 * creates the RoomManagement-instance
	 */
	private RoomManagement()
	{
		
	}
	
	/**
	 * 
	 * @return the instance of RoomManagement or creates a new one if there is none
	 */
	public static synchronized RoomManagement getInstance()
	{
		if(instance == null)
		{
			instance = new RoomManagement();
		}
		return RoomManagement.instance;
	}

	
	/**
	 * adds a Room-object to the room-map
	 * @param name Name of the room that is to be added. It has to be unique
	 * @param number Number of the room that is to be added. It has to be unique
	 * @param chairNum Number of chairs in the room
	 * @return
	 */
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
	
	/**
	 * 
	 * @return A list of all rooms
	 */
	public List<Room> getAllRooms()
	{
		List<Room> allRooms = new ArrayList<Room>(rooms.values());
		return allRooms;
	}
	
	/**
	 * 
	 * @return A list of all room names
	 */
	public List<String> getAllRoomNames()
	{
		List<String> allNames = new ArrayList<String>();
		allNames.addAll(rooms.keySet());
		return allNames;
	}
}
