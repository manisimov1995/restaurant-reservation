package restaurant.handlers;

import java.util.Date;
import java.util.Map;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import restaurant.database.RestaurantCache;

public class CustomerHandler extends AbstractHandler {

	@Override
	public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
		Date date = getDate(request);
		String name = getName(request);
		Integer size = getSize(request);
		Integer timeSlot = getTimeSlot(request);
		if (date == null) {
			response.write("Missing or invalid date");
		} else if (name == null) {
			response.write("Missing or invalid customer name");
		} else if (size == null) {
			response.write("Missing or invalid table size");
		} else if (timeSlot == null) {
			response.write("Missing or invalid hour (24)");
		} else {
			RestaurantCache cache = RestaurantCache.getInstance();
			if (cache.book(size, date, timeSlot, name)) {
				response.write(name + ", a table is booked");
			} else {
				response.write(name + ", no table is available for this date and time");
			}
		}
	}
}
