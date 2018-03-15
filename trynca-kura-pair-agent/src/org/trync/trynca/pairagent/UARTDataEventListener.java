package org.trync.trynca.pairagent;

import java.io.IOException;
import java.util.Map;

import org.eclipse.osgi.framework.eventmgr.EventManager;
import org.slf4j.Logger;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

public class UARTDataEventListener implements SerialDataEventListener {
	private Logger s_logger;
	private Map<String, Object> properties;

	public UARTDataEventListener(Logger s_logger, Map<String, Object> properties) {
		this.s_logger = s_logger;
		this.properties = properties;
	}

	@Override
	public void dataReceived(SerialDataEvent event) {
		event.getSerial().removeListener(this);
		try { // console.println("[HEX DATA] " + event.getHexByteString());
				// console.println("[ASCII DATA] " + event.getAsciiString());
			String serialData = event.getAsciiString();
			String pairSeperator = "|";
			String pairParams = "TRYNC" + pairSeperator;
			pairParams += properties.get("wifissid").toString() + pairSeperator;
			pairParams += properties.get("wifipassword").toString() + pairSeperator;
			pairParams += properties.get("mqttbroker").toString() + pairSeperator;
			pairParams += properties.get("mqttuser").toString() + pairSeperator;
			pairParams += properties.get("mqttpasswd").toString() + pairSeperator;
			

			s_logger.info("Sending : " + pairParams+pairParams);
			event.getSerial().write(pairParams + "\r\n");
		} catch (IOException e) {
			s_logger.error(e.getMessage());
		}
		event.getSerial().addListener(this);
	}
}
