package org.trync.trynca.pairagent;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;

public class PairAgent implements ConfigurableComponent {
	private static final Logger s_logger = LoggerFactory.getLogger(org.trync.trynca.pairagent.PairAgent.class);
	private static final String APP_ID = "org.trync.trynca.pairagent.PairAgent";
	final Serial serial = SerialFactory.createInstance();
	UARTCommunicator ucom = null;


	protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
		s_logger.info("Bundle " + APP_ID + " has started with config!");
		updated(properties);
	}

	protected void deactivate(ComponentContext componentContext) {
		s_logger.info("Bundle " + APP_ID + " has stopped!");
		try {
			if (ucom != null) {
				ucom.destroy(serial);
			}
		} catch (InterruptedException | IOException e) {
			s_logger.error(e.getMessage());
		}
	}

	public void updated(Map<String, Object> properties) {
		try {
			ucom = new UARTCommunicator();
			ucom.init(s_logger, serial,properties);
		} catch (InterruptedException | IOException e) {
			s_logger.error(e.getMessage());
		}
	}
}
