package com.Producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerWithKeys {
	
	public static void main(String[] args) {
		String topicname = "xyz";
		
		//Properties needed to create an producer
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		
		for(int i = 0; i<5; i++) {
			String key = "user "+(i%2);
			String message = "message "+i;
			ProducerRecord<String,String> record = new ProducerRecord<String, String>(topicname, key, message);
			producer.send(record, (metadata,exception) -> {
				
				if(exception ==null) {
					System.out.printf("sent [key=%s] to partition %d at offset %d%n",key,metadata.partition(),metadata.offset());
				}
				else {
					System.out.println("Error sending message: "+exception.getMessage());
				}
			});
			
		}
		
		producer.close();
	}

}
