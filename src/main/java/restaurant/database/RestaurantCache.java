package restaurant.database;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RestaurantCache {

	// singleton
	private static RestaurantCache instance = null;

	public static synchronized RestaurantCache getInstance() {
		if (instance == null) {
			instance = new RestaurantCache();
		}
		return instance;
	}

	// Tables available in the restaurant
	// key - table size
	// value - number of tables
	private Map<Integer, RestaurantTable[]> tables;

	private RestaurantCache() {
		tables = new HashMap<>();
		int next = 1;
		next = initTables(2, 3, next);
		initTables(4, 2, next);
	}

	private int initTables(int tableSize, int n, int next) {
		RestaurantTable[] restaurantTables = new RestaurantTable[n];
		for (int i = 0; i < n; i++) {
			restaurantTables[i] = new RestaurantTable(next++);
		}
		tables.put(tableSize, restaurantTables);
		return next;
	}

	// For unit tests only
	public static RestaurantCache getCacheForTest() {
		return new RestaurantCache();
	}

	/*
	 * Customer operations
	 */

	public synchronized boolean book(int tableSize, Date date, int timeSlot, String name) {
		boolean booked = false;
		RestaurantTable[] restaurantTables = tables.get(tableSize);
		if (restaurantTables != null) {
			for (int i = 0; i < restaurantTables.length; i++) {
				if (restaurantTables[i].book(date, timeSlot, name)) {
					booked = true;
					break;
				}
			}
		}
		return booked;
	}

	/*
	 * Owner operations
	 */

	public static class Reservation {

		public int tableId;
		public int tableSize;
		public int hr24;
		public String name;

		public Reservation(int tableId, int tableSize, int timeSlot, String name) {
			this.tableId = tableId;
			this.tableSize = tableSize;
			this.hr24 = timeSlot * 2;
			this.name = name;
		}

		public int getTableId() {
			return tableId;
		}

		public int getTableSize() {
			return tableSize;
		}

		public int getHr24() {
			return hr24;
		}

		public String getName() {
			return name;
		}
	}

	public synchronized List<Reservation> allReservations(Date date) {
		List<Reservation> list = new LinkedList<>();
		Set<Entry<Integer, RestaurantTable[]>> entries = tables.entrySet();
		for (Entry<Integer, RestaurantTable[]> entry : entries) {
			for (RestaurantTable restaurantTable : entry.getValue()) {
				list.addAll(restaurantTable.allReservations(date, entry.getKey()));
			}
		}
		return list;
	}
}
