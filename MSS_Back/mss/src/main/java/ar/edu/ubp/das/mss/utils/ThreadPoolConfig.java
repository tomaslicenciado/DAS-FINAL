package ar.edu.ubp.das.mss.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
    
    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

    public static void waitForCompletion(ThreadPoolTaskExecutor taskExecutor) throws Exception {
        while (taskExecutor.getActiveCount() > 0) {
            Thread.sleep(100);
        }
    }
}
