/**
 * 
 */
package in.anandm.yell;

import java.io.IOException;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Anand
 * 
 */
@AtmosphereHandlerService(path = "/yellRoom", interceptors = {
		AtmosphereResourceLifecycleInterceptor.class,
		BroadcastOnPostAtmosphereInterceptor.class,
		TrackMessageSizeInterceptor.class, HeartbeatInterceptor.class })
public class YellRoom extends OnMessage<String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onMessage(AtmosphereResponse response, String message)
			throws IOException {
		Yell yell = mapper.readValue(message, Yell.class);
		response.write(mapper.writeValueAsString(yell));
	}
}
