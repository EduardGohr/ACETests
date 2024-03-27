package de.onpoint.amqp.client;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class Sender {
	
	public Sender(Connection connection) {
		super();
		
		try {
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
		} catch (JMSException e) {
			System.err.println(e.toString());
		}
	}

	private Session session;

	public void sendMessage2Topic(String tName, String message) {
        try {
			MessageProducer producer = session.createProducer(session.createTopic(tName));
			producer.send(session.createTextMessage(message));
			producer.close();
			session.close();
		} catch (JMSException e) {
			System.err.println(e.toString());
		}
		
	}
}
