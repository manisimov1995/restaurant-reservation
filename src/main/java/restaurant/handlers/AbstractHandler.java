package restaurant.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.muserver.MuRequest;
import io.muserver.RouteHandler;

abstract class AbstractHandler implements RouteHandler {

	private static final String PAR_DATE = "date";
	private static final String PAR_NAME = "name";
	private static final String PAR_SIZE = "tsize";
	private static final String PAR_HR24 = "hr24";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	protected Date getDate(MuRequest request) {
		Date date = null;
		String parDate = request.query().get(PAR_DATE);
		if (parDate != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			try {
				date = format.parse(parDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				date = cal.getTime();
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}

	protected String getName(MuRequest request) {
		return request.query().get(PAR_NAME);
	}

	protected Integer getSize(MuRequest request) {
		Integer size = null;
		String parSize = request.query().get(PAR_SIZE);
		if (parSize != null) {
			try {
				size = Integer.parseInt(parSize);
				if (size != 2 && size != 4) {
					size = null;
				}
			} catch(NumberFormatException e) {
				size = null;
			}
		}
		return size;
	}

	protected Integer getTimeSlot(MuRequest request) {
		Integer timeSlot = null;
		String parHour = request.query().get(PAR_HR24);
		if (parHour != null) {
			try {
				int hour = Integer.parseInt(parHour);
				if (hour % 2 == 0 && (hour >= 0 || hour <= 24)) {
					timeSlot = (hour == 24) ? 0 : hour / 2;
				}
			} catch(NumberFormatException e) {
				timeSlot = null;
			}
		}
		return timeSlot;
	}
}
