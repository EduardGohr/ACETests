package de.onpoint.amqp.client;

import org.apache.qpid.jms.JmsConnectionFactory;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;

public class ConnectionManager {
	
	public ConnectionManager(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		
        ConnectionFactory cf = new JmsConnectionFactory("amqp://" + this.host + ":" + this.port + "?amqp.idleTimeout=-1");
        try {
			this.connection = cf.createConnection();
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
