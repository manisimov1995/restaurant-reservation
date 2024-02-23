package restaurant.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurant.database.RestaurantCache.Reservation;

class RestaurantTable {

	private int tableId;
	// Assuming the restaurant is open 7/24
	// key - date only, without time
	// value - array of 12 customer names or nulls
	private Map<Date, String[]> bookings;

	public RestaurantTable(int tableId) {
		this.tableId = tableId;
		bookings = new HashMap<>();
	}

	public boolean book(Date date, int timeSlot, String name) {
		boolean booked = false;
		String[] names = bookings.get(date);
		if (names == null) {
			names = new String[12];
			for (int i = 0; i < names.length; i++) {
				names[i] = null;
			}
			bookings.put(date, names);
		}
		if (names[timeSlot] == null) {
			names[timeSlot] = name;
			booked = true;
		}
		return booked;
	}

	public List<Reservation> allReservations(Date date, int tableSize) {
		List<Reservation> list = new ArrayList<>();
		String[] names = bookings.get(date);
		if (names != null) {
			for (int i = 0; i < names.length; i++) {
				if (names[i] != null) {
					list.add(new Reservation(tableId, tableSize, i, names[i]));
				}
			}
		}
		return list;
	}
}
