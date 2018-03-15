package org.trync.trynca.pairagent;

import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

public class UARTCommunicator {
	private Logger s_logger;
	private Serial serial;
	private Map<String, Object> properties;

	public void init(Logger s_logger, Serial serial, Map<String, Object> properties)
			throws InterruptedException, IOException {
		this.s_logger = s_logger;// logger
		this.serial = serial;// serial
		this.properties = properties;

		// create and register the serial data listener
		serial.addListener(new UARTDataEventListener(s_logger,properties));

		try {
			// create serial config object
			SerialConfig config = new SerialConfig();

			config.device(SerialPort.getDefaultPort()).baud(Baud._38400).dataBits(DataBits._8).parity(Parity.NONE)
					.stopBits(StopBits._1).flowControl(FlowControl.NONE);
			// display connection details
			s_logger.warn(" Connecting to: " + config.toString());
			serial.open(config);
			//serial.write((" Connecting to: " + config.toString()));
		} catch (IOException ex) {
			s_logger.error(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
			return;
		}
	}

	public void destroy(Serial serial) throws InterruptedException, IOException {
		serial.discardAll();
		serial.flush();
		serial.close();
	}

}
