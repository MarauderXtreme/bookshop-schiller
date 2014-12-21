package bookshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bookshop.AbstractIntegrationTests;

public class RoomManagerTest extends AbstractIntegrationTests {
	
	@Test
	public void testRoomManager()
	{		
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",true, RoomManagement.getInstance().addRoom("Testraum1", "111", "40"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",false, RoomManagement.getInstance().addRoom("Testraum1","112","234"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",false,RoomManagement.getInstance().addRoom("Testraum3","111","234"));
		assertEquals("Ein Raum wird dem RoomManagement hinzugefuegt",true,RoomManagement.getInstance().addRoom("Testraum4","114","24"));
		
		
		
	}

}
