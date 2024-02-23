package restaurant.handlers;

import java.util.Map;

import io.muserver.ContentTypes;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

public class DefaultHandler implements RouteHandler {

	@Override
	public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
		response.contentType(ContentTypes.TEXT_HTML);
		StringBuilder b = new StringBuilder();
		b.append("<h3>Customer URL example</h3>");
		b.append("<p>http://localhost/customer?name=Tim%20Hortons&date=2024-03-15&hr24=18&tsize=2</p>");
		b.append("<h3>Owner URL example</h3>");
		b.append("<p>http://localhost/owner?date=2024-03-15</p>");
		response.write(b.toString());
	}
}
