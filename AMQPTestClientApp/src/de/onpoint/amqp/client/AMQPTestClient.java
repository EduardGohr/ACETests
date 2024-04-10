package de.onpoint.amqp.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jakarta.jms.Connection;


public class AMQPTestClient {

	public static void main(String[] args) {
	    Options options = new Options();

        Option modeO = new Option("m", "mode", true, "sender/receiver mode");
        modeO.setRequired(true);
        options.addOption(modeO);
        options.addOption(new Option("h", "host", true, "hostname"));
        options.addOption(new Option("p", "port", true, "port"));

        options.addOption(new Option("t", "topic", true, "topic name"));
        options.addOption(new Option("q", "queue", true, "queue name"));
        options.addOption(new Option("inF", "inFile", true, "input file name"));
        options.addOption(new Option("outF", "outFile", true, "output file name"));
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null; 

        try {
            cmd = parser.parse(options, args);
            if (cmd.getOptionValue("t") == null && cmd.getOptionValue("q") == null) {
            	System.out.println("Please provide a queue name or topic name!");
            	System.exit(1);
            }
            
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("AMQP Test Client", options);

            System.exit(1);
        }

        String mode = cmd.getOptionValue("mode");
        String host = cmd.getOptionValue("host") != null ? cmd.getOptionValue("host") : "localhost";
        int port = cmd.getOptionValue("port") != null ? Integer.parseInt(cmd.getOptionValue("port")) : 5672;
        String queue = cmd.getOptionValue("queue");
        String topic = cmd.getOptionValue("topic");
        String inFile = cmd.getOptionValue("inFile");
        String outFile = cmd.getOptionValue("outFile");
        
		if (mode.equals("send")) {	
			if (inFile == null) {
				System.out.println("Please provide an input file name!");
				System.exit(1);
			}
			
			String inputContent = "";
			try {
				inputContent = new String(Files.readAllBytes(Paths.get(inFile)));
	        } catch (IOException e) {
	        	System.out.println(e.getMessage());
	        }			
			ConnectionManager cm = new ConnectionManager(host, port);
			Connection connection = cm.getConnection();
			Sender sender = new Sender(connection);
			
			if (topic != null)
				sender.sendMessage2Topic(topic, inputContent);
			else
				sender.sendMessage2Queue(queue, inputContent);
		}
		
		if (mode.equals("receive")) {	
			ConnectionManager cm = new ConnectionManager(host, port);
			Connection connection = cm.getConnection();
			Receiver receiver = new Receiver(connection);
			
			if (topic != null)
				receiver.receiveTextMessageFromTopic(topic);
			else
				receiver.receiveTextMessageFromQueue(queue);
			
			if (!receiver.getMessageText().isEmpty()) {
				if (outFile != null) {
					try {
			            Files.write(Paths.get(outFile), receiver.getMessageText().getBytes());
			        } catch (IOException e) {
			            e.printStackTrace();
			        }					
				}				
			}
			else {
				System.out.println("No messages received!");
			}
		}
		
		System.exit(0);
	}

}
