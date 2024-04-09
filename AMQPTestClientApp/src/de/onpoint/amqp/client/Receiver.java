package de.onpoint.amqp.client;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class Receiver implements MessageListener {

	public Receiver(Connection connection) {
		super();

		try {
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
		} catch (JMSException e) {
			System.err.println(e.toString());
		}
		
	}
	
	public void receiveTextMessageFromTopic(String tName) {
        try {
			MessageConsumer consumer = session.createConsumer(session.createTopic(tName));
			consumer.setMessageListener(this);
			
			Thread.sleep(20000);
						
			if (consumer != null) { 
				consumer.close();
			}
			
			session.close();
			
		} catch (JMSException | InterruptedException e) {
			System.err.println(e.getMessage());
		}
        
	}

	public void receiveTextMessageFromQueue(String qName) {
        try {
			MessageConsumer consumer = session.createConsumer(session.createQueue(qName));
			consumer.setMessageListener(this);
			
			Thread.sleep(20000);
			
			if (consumer != null) { 
				consumer.close();
			}
			
			session.close();
			 
		} catch (JMSException | InterruptedException e) {
			System.err.println(e.toString());
		}
        
	}
	
	private Session session;
	

	@Override
	public void onMessage(Message message) {
		try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message: " + textMessage.getText());
            } else {
                System.out.println("Received message of unsupported type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }	}

	public Session getSession() {
		return session;
	}
	
}
