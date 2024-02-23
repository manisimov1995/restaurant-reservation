import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import restaurant.database.RestaurantCache;
import restaurant.database.RestaurantCache.Reservation;

public class CacheTest {

	private static final String NAME1 = "Steve";
	private static final String NAME2 = "Tim";

	private RestaurantCache cache;
	private Date date;

	@Before
	public void before() {
		cache = RestaurantCache.getCacheForTest();
		date = new Date();
	}

	@Test
	public void testOne() {
		boolean r = cache.book(4, date, 7, NAME1);
		Assert.assertTrue(r);
		List<Reservation> list = cache.allReservations(date);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testTwo() {
		boolean r = cache.book(4, date, 7, NAME1);
		Assert.assertTrue(r);
		r = cache.book(2, date, 8, NAME2);
		Assert.assertTrue(r);
		List<Reservation> list = cache.allReservations(date);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void testTooMany() {
		boolean r = cache.book(4, date, 7, NAME1);
		Assert.assertTrue(r);
		r = cache.book(4, date, 7, NAME1);
		Assert.assertTrue(r);
		r = cache.book(4, date, 7, NAME1);
		Assert.assertFalse(r);
		List<Reservation> list = cache.allReservations(date);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}
}
