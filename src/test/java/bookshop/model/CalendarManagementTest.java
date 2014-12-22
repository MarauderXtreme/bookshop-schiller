package bookshop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.validation.constraints.AssertFalse;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class CalendarManagementTest extends AbstractIntegrationTests {
		
	@Test
	public void testCalendarEvents()
	{
		RoomManagement.getInstance().addRoom("Testraum1", "111", "40");
		 RoomManagement.getInstance().addRoom("Testraum2","114","24");

		MyDate testdate = new MyDate("11092014","1110");
		MyDate testdate2 = new MyDate("10092014","1110");
		
		Event testevent1 = new Event("Testevent 1", testdate, RoomManagement.getInstance().getRoom("Testraum1"));
		Event testevent2 = new Event("Testevent 2", testdate2, RoomManagement.getInstance().getRoom("Testraum2"));
		Event testevent3 = new Event("Testevent 3", testdate2, RoomManagement.getInstance().getRoom("Testraum2"));
		Event testevent4 = new Event("Testevent 4", testdate, RoomManagement.getInstance().getRoom("Testraum2"));
		
		
		assertTrue("Die Methode addEvent() des Calendar-objekts hat einen fehler  ", CalendarManagement.getInstance().getCalendar().addEvent(testevent1));
		assertEquals("die Methode addEvent() des Calendar-objekts added ein ungueltiges Event", true, CalendarManagement.getInstance().getCalendar().addEvent(testevent2));
		assertEquals("die Methode addEvent() des Calendar-objekts added ein event doppelt, nimmt es aber dennoch an", false, CalendarManagement.getInstance().getCalendar().addEvent(testevent2));
		assertEquals("die Methode addEvent() des Calendar-objekts added ein event an einem belegten Datum", false, CalendarManagement.getInstance().getCalendar().addEvent(testevent3));
		assertEquals("die Methode addEvent() des Calendar-objekts added ein ungueltiges event", false, CalendarManagement.getInstance().getCalendar().addEvent(testevent3));
		
		assertEquals("die Methode removeEvent() des Calendar-Objekts kann ein Objekt nicht removen",true,CalendarManagement.getInstance().getCalendar().removeEvent(new TupelKey<Room,MyDate>(testevent1.getRoom(), testevent1.getDate())));
		assertEquals("die Methode removeEvent() des Calendar-Objekts entfernt ein Objekt das nicht existiert",false,CalendarManagement.getInstance().getCalendar().removeEvent(new TupelKey<Room,MyDate>(testevent1.getRoom(), testevent1.getDate())));
	}


}
