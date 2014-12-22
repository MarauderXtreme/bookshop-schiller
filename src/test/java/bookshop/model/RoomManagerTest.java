package bookshop.model;

import static org.junit.Assert.*;

import org.junit.Test;

import bookshop.AbstractIntegrationTests;

public class RoomManagerTest extends AbstractIntegrationTests {
	
	@Test
	public void testRoomManager()
	{		
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",true, RoomManagement.getInstance().addRoom("Raum0001", "1010101", "40"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",false, RoomManagement.getInstance().addRoom("Raum0001","112","234"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",false,RoomManagement.getInstance().addRoom("Raum0011","1010101","234"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",true,RoomManagement.getInstance().addRoom("Raum0100","0110110","24"));
		
		assertTrue("Ein Raum kann nicht entfernt werden",RoomManagement.getInstance().removeRoom("Testraum1"));
		assertEquals("Ein Raum wird entfernt,obwohl es ihn nicht gibt",false,RoomManagement.getInstance().removeRoom("Testraum1"));
		
		
		
	}

}
