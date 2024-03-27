package de.onpoint.amqp.client;

import javax.jms.Connection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class AMQPTestClient {

	public static void main(String[] args) {
	    Options options = new Options();

        Option mode = new Option("m", "mode", true, "sender/receiver mode");
        mode.setRequired(true);
        options.addOption(mode);

        Option topic = new Option("t", "topic", true, "topic name");
        options.addOption(topic);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null; 

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("AMQP Test Client", options);

            System.exit(1);
        }

        String modeV = cmd.getOptionValue("mode");
        String outputFilePath = cmd.getOptionValue("output");	
        
		
		if (modeV.equals("send")) {			
			ConnectionManager cm = new ConnectionManager("localhost", 5672);
			Connection connection = cm.getConnection();
			Sender sender = new Sender(connection);
			sender.sendMessage2Topic("hello-world-example", "foo bar");
		}
		
		if (modeV.equals("receive")) {	
			ConnectionManager cm = new ConnectionManager("localhost", 5672);
			Connection connection = cm.getConnection();
			Receiver receiver = new Receiver(connection);
			receiver.receiveTextMessageFromTopic("hello-world-example");
			
		}
		
		System.exit(0);
	}

}
