package de.onpoint.amqp.client;

import javax.jms.*;

import org.apache.qpid.jms.JmsConnectionFactory;

public class ConnectionManager {
	
	public ConnectionManager(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		
        ConnectionFactory cf = new JmsConnectionFactory("amqp://" + this.host + ":" + this.port + "?amqp.idleTimeout=-1");
        try {
			this.connection = cf.createConnection();
			System.out.println("ClientID: " + this.connection.getClientID());
		} catch (JMSException e) {
			System.err.println(e.toString());
		}
		
	}
	private String host;
	private int port;
	private Connection connection;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Connection getConnection() {
		return connection;
	}
		
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				System.err.println(e.toString());
			}
		}
	}
	

}
