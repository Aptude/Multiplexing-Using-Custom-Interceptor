import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
public class CustomInterceptor implements Interceptor {
	@Override
	public void close() {
	}
	@Override
	public void initialize() {
	}
	@Override
	public Event intercept(Event event) {
		try {
			// This is the event's body
			String body = new String(event.getBody());
			// These are the event's headers
			Map<String, String> headers = event.getHeaders();
			String fullPath = event.getHeaders().get("file");
			String[] fileparts = fullPath.split("/");
			String filename = fileparts[fileparts.length - 1];
			String[] ltn = filename.split("_");
			String[] fileString = ltn[2].split("\\.");
			String logfilename = fileString[0];
			headers.put("fileloc", logfilename);
		} catch (Exception e) {
			System.out.println(e);
		}
		return event;
	}
	@Override
	public List<Event> intercept(List<Event> events) {
		List<Event> interceptedEvents = new ArrayList<Event>(events.size());
		for (Event event : events) {
			// Intercept any event
			Event interceptedEvent = intercept(event);
			interceptedEvents.add(interceptedEvent);
		}
		return interceptedEvents;
	}
	public static class Builder implements Interceptor.Builder {
		@Override
		public void configure(Context context) {
			// TODO Auto-generated method stub
		}

		@Override
		public Interceptor build() {
			return new CustomInterceptor();
		}
	}
}
