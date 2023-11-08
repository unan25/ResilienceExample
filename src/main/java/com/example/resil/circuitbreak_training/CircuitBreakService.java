package com.example.resil.circuitbreak_training;

import com.example.resil.exception.IgnoreException;
import com.example.resil.retry_training.RetryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CircuitBreakService {

    private static final String CIRCUIT_BREAKER_CONFIG = "circuitBreakerConfig";

    // 애너테이션 @CircuitBreaker를 통한 메서드는 회로차단기의 대상이 된다.
    @CircuitBreaker(name = CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallback")
    public String process(String param) {
        return callFeignClient(param);
    }

    private String fallback(String param, Exception e){
        // circuit breaker의 fallback은 IgonoreException에 의해서도 실행된다.
        // 다만 IgnoreException에 등록된 유형의 예외를 통해서는 open 상태로의 전환은 이뤄지지 않는다.
        System.out.println(param + "파라미터가 예외를 유발했습니다.");
        return "예외 종류 : " + e.toString();
    }

    private String callFeignClient(String param) {
        if("a".equals(param))
            throw new RuntimeException("A 유형 예외 발생");
        else if("b".equals(param))
            throw new IgnoreException("B 유형 예외 발생");

        return param;
    }
}
