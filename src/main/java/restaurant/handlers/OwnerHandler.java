package restaurant.handlers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.muserver.ContentTypes;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import restaurant.database.RestaurantCache;
import restaurant.database.RestaurantCache.Reservation;

public class OwnerHandler extends AbstractHandler {

	@Override
	public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
		Date date = getDate(request);
		if (date == null) {
			response.write("Missing or invalid date");
		} else {
			RestaurantCache cache = RestaurantCache.getInstance();
			List<Reservation> list = cache.allReservations(date);
			response.contentType(ContentTypes.TEXT_HTML);
			if (list.isEmpty()) {
				response.write("<p>No reservation for this date</p>");
			} else {
				StringBuilder b = new StringBuilder("<table>");
				b.append("<tr height=50>");
				b.append("<td width=100><b>Table size</b></td>");
				b.append("<td width=100><b>Table ID</b></td>");
				b.append("<td width=100><b>Hour (24)</b></td>");
				b.append("<td width=200><b>Customer name</b></td>");
				b.append("</tr>");
				for (Reservation reservation : list) {
					b.append("<tr><td>");
					b.append(reservation.getTableSize()).append("</td><td>");
					b.append(reservation.getTableId()).append("</td><td>");
					b.append(reservation.getHr24()).append("</td><td>");
					b.append(reservation.getName());
					b.append("</td></tr>");
				}
				b.append("</table>");
				response.write(b.toString());
			}
		}
	}
}
