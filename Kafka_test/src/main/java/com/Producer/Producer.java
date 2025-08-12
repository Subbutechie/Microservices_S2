package com.Producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.*;


public class Producer {
	
	public static void main(String[] args) {
		
		String topicname = "xyz";
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		
		for(int i =0; i<5; i++) {
			String message = "message"+i;
			producer.send(new ProducerRecord<String, String>(topicname, message));
			System.out.println("Sent message: "+message);

		}
		producer.send(new ProducerRecord<String, String>(topicname, "Hi sending message using Java Producer"));
		producer.send(new ProducerRecord<String, String>(topicname, "Hey! How are you!!"));
		
		producer.close();
		
	}
	

}
