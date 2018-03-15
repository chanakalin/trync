package org.trync.daemon.subscribers;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.logging.Logger;
import org.trync.obj.GatewayObject;

public class gatewayCallback implements MqttCallback {

	private static final Logger LOGGER = Logger.getLogger(gatewayCallback.class);

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		LOGGER.info("Topic : " + topic+" --- Msg : " + message.toString());

		try {
			JSONObject mqttMessageJson = new JSONObject(message.toString());
			JSONObject mqttMessageJsonMetrics = new JSONObject(mqttMessageJson.getString("metrics"));
			String[] topicSplitted = topic.split("/");

			if (topicSplitted[topicSplitted.length - 1].contains("BIRTH")) {// birth
				GatewayObject gateway = new GatewayObject();
				gateway.setAccName(topicSplitted[2]);
				gateway.setGwName(topicSplitted[3]);
				gateway.setDisplayName(mqttMessageJsonMetrics.getString("display_name"));
				gateway.setConnectionIp(mqttMessageJsonMetrics.getString("connection_ip"));
				gateway.setConnectionInterfaces(mqttMessageJsonMetrics.getString("connection_interface"));
				gateway.setSerialNumber(mqttMessageJsonMetrics.getString("serial_number"));
				gateway.setModelId(mqttMessageJsonMetrics.getString("model_id"));
				if (gateway.giveBirth()) {
					LOGGER.info("Gateway birth : " + mqttMessageJsonMetrics.getString("display_name"));
				} else {
					LOGGER.error("Gateway birth : " + mqttMessageJsonMetrics.getString("display_name"));
				}
			} else if (topicSplitted[topicSplitted.length - 1].contains("DC")) {// disconnect
				GatewayObject gateway = new GatewayObject(topicSplitted[3]);
				gateway.setStatus(false);
				if (gateway.update()) {
					LOGGER.warn("Gateway disconnected : " + mqttMessageJsonMetrics.getString("display_name"));
				}else {
					LOGGER.error("Gateway disconnected : " + mqttMessageJsonMetrics.getString("display_name") + " (update failed)");
				}
			}
		} catch (JSONException e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		LOGGER.info("Delivery completed : " + arg0.getMessageId());

	}

	@Override
	public void connectionLost(Throwable arg0) {
		LOGGER.error("connection lost : " + arg0);
	}
}
