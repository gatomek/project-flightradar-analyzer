package pl.gatomek.flightradar.analyzer.adapter.in.rabbit.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@EnableConfigurationProperties(RabbitPropertiesConfiguration.class)
public class RabbitConfig {
}
