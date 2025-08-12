package com.Consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
	
	public static void main(String[] args) {
		
		//Here Consumer needs to deseriliaize while pulling it
		//because it will be serilaized to byte array before writing to topic
		//So we need a deserializer 
		String topicName = "xyz";
		String deseriliazer = "org.apache.kafka.common.serialization.StringDeserializer";
		
		
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "Java-consumer-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deseriliazer);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deseriliazer);
		
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Collections.singletonList(topicName));
		
		System.out.println("Waiting for messages...");
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
			for(ConsumerRecord<String, String> record : records) {
				System.out.println(record.value());
				System.out.println(record.key());
			}
		}
		
		
	}

}
