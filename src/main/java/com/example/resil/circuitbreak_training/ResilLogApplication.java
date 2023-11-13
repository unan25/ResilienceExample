package com.example.resil.circuitbreak_training;

/*
 메인메서드를 담당하는 어플리케이션을 하나의 프로젝트 내에 2개 이상 생성할 수 있다.
 이 경우 어떤 메인메서드를 실행하는지에 따라 주입되는 빈의 종류를 다르게 하거나 특정한 상황에만 빈을 주입할 수 있다.
 */

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ResilLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResilLogApplication.class, args);
    }
    
    //로깅용 이벤트 빈을 같이 등록하는 어플리케이션
    @Bean
    public RegistryEventConsumer<CircuitBreaker> customRegistryEventConumer() {
        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                log.info("RegistryEventConsumer의 onEntryAddedEvent 발생");
//                entryAddedEvent.getAddedEntry()
//                        .getEventPublisher()
//                        .onEvent(event -> log.info(event.toString()));
//                entryAddedEvent.getAddedEntry()
//                        .getEventPublisher()
//                        .onFailureRateExceeded(event -> log.info("{}", event.getEventType()));
                
                // 좀 더 상세하게
                CircuitBreaker.EventPublisher eventPublisher =
                        entryAddedEvent.getAddedEntry().getEventPublisher();
                eventPublisher.onEvent(event -> log.info("onEvent {}", event));
                eventPublisher.onSuccess(event -> log.info("onSuccess {}", event));
                eventPublisher.onStateTransition(event -> log.info("onStateTransaction {}", event));
                eventPublisher.onCallNotPermitted(event -> log.info("onCallNotPermitted {}", event));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

            }
        };

    }

}
