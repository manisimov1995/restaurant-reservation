package restaurant;

import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import restaurant.handlers.CustomerHandler;
import restaurant.handlers.DefaultHandler;
import restaurant.handlers.OwnerHandler;

/*
 * Customer operations
 * 
 * 		Book table request parameters
 * 			name	- customer name
 * 			tsize	- table size (2 or 4)
 * 			date	- date formatted as yyyy-MM-dd
 * 			hr24	- hour of day (0 to 24; 24 equals 0)
 *
 * Owner operations
 * 
 * 		List bookings request parameters
 * 			date	- date formatted as yyyy-MM-dd
 * 
 */
public class RestaurantReservation {

	public static void main(String[] args) {
        MuServer server = MuServerBuilder.httpServer()
                .addHandler(Method.GET, "/", new DefaultHandler())
                .addHandler(Method.GET, "/customer", new CustomerHandler())
                .addHandler(Method.GET, "/owner", new OwnerHandler())
                .withHttpPort(80)
                .start();
            System.out.println("Started server at " + server.uri());
	}
}
