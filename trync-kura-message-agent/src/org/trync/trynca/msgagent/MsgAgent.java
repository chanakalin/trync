package org.trync.trynca.msgagent;

import java.io.IOException;
import java.util.Map;

import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgAgent  implements ConfigurableComponent{
	private static final Logger s_logger = LoggerFactory.getLogger(org.trync.trynca.msgagent.MsgAgent.class);
	private static final String APP_ID = "org.trync.trynca.msgagent.MsgAgent";
	
	protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
		s_logger.info("Bundle " + APP_ID + " has started with config!");
		updated(properties);
	}

	protected void deactivate(ComponentContext componentContext) {
		s_logger.info("Bundle " + APP_ID + " has stopped!");
		

	}

	public void updated(Map<String, Object> properties) {
		
	}
}
