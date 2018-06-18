package com.github.sundarsy.producer;

import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@EnableBinding(Source.class)
public class StreamProducerApplication {

	protected Logger logger = LoggerFactory.getLogger(StreamProducerApplication.class.getName());

	private int index = 0;

	public static void main(String[] args) {
		SpringApplication.run(StreamProducerApplication.class, args);
	}
	
	@Bean	
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<String> orderSource() {
		return () -> {
			String str = "My Message" + Instant.now();
			logger.info("Sending Message: " + str);
			return new GenericMessage<>(str);
		};
	}

/*	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}*/

}
