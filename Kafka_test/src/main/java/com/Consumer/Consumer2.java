package com.Consumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer2 {

	public static void main(String[] args) {
		
		String topicName ="xyz";
		
		Properties props = new Properties();
		try {
			FileInputStream reader = new FileInputStream("C:\\Users\\pudu.reddy\\Apache_Kafka\\Kafka_test\\src\\main\\resources\\consumer2.properties");
			props.load(reader);
		}catch(IOException e) {
			System.out.println(e);
			return ;
		}
		
		
		KafkaConsumer<String, String> consumer2 = new KafkaConsumer<String, String>(props);
		consumer2.subscribe(Collections.singletonList(topicName));
		
		while(true) {
			ConsumerRecords<String, String> records = consumer2.poll(Duration.ofSeconds(1));
			for(ConsumerRecord<String, String> record : records) {
				System.out.println(record.value());
			}
		}
	}
}
