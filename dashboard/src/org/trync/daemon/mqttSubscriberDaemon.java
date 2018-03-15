package org.trync.daemon;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.trync.daemon.subscribers.gatewayCallback;

public class mqttSubscriberDaemon implements ServletContextListener {

	/**
	 * 
	 * @author Chanaka Madushan Jayalath
	 *
	 *         Start the daemon task at app server startup
	 */
	
	private static final Logger LOGGER = Logger.getLogger(mqttSubscriberDaemon.class);
	
	String brokerUrl = "tcp://trync.org:1883";
	String clientId = "tryncd";
	int qos = 2;
	MemoryPersistence persistence = new MemoryPersistence();
	MqttConnectOptions connOpts = new MqttConnectOptions();
	
	
	MqttClient gatewayClient = null;
	String gatewayTopic = "/trync/+/+/MQTT/+";

	/**
	 * Create MQTT Subscribers and start app
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//connnection option
		connOpts.setCleanSession(true);
		connOpts.setUserName("tryncd");
		connOpts.setPassword(("m@ls@r@").toCharArray());
		
		
		try {
			//gateway
			gatewayClient = new MqttClient(brokerUrl, clientId, persistence);
			MqttCallback gatewaycallback = new gatewayCallback();
			gatewayClient.setCallback(gatewaycallback);
			gatewayClient.connect(connOpts);
			if(gatewayClient.isConnected()) {
				gatewayClient.subscribe(gatewayTopic);
			}
			
			
		} catch (MqttException me) {
			LOGGER.error(me.getMessage());
		}
	}

	/**
	 * Shutdown MQTT Subscribers and app
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			gatewayClient.disconnect();
			LOGGER.info("Disconnected from "+gatewayClient.getServerURI());
		} catch (MqttException me) {
			LOGGER.error(me.getMessage());
		}
	}

}
