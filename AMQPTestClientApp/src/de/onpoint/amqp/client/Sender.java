package de.onpoint.amqp.client;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;

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
	
	public void sendMessage2Queue(String qName, String message) {
        try {
			MessageProducer producer = session.createProducer(session.createQueue(qName));
			producer.send(session.createTextMessage(message));
			producer.close();
			session.close();
		} catch (JMSException e) {
			System.err.println(e.toString());
		}
		
	}

}
