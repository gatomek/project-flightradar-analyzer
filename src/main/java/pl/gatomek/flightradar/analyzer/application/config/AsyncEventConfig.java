package pl.gatomek.flightradar.analyzer.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncEventConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        var eventMulticaster = new SimpleApplicationEventMulticaster();

        SimpleAsyncTaskExecutor ex = new SimpleAsyncTaskExecutor("event-");
        ex.setConcurrencyLimit(50);
        ex.setVirtualThreads(true);
        ex.setTaskTerminationTimeout(30_000);

        eventMulticaster.setTaskExecutor(ex);
        return eventMulticaster;
    }
}
