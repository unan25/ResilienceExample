package com.example.resil.retry_training;

import com.example.resil.exception.IgnoreException;
import com.example.resil.exception.RetryException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class RetryService {
    // retry에 관련된 config 파일 이름을 지정하는 상수
    private static final String CONFIG_CLASS_NAME = "retryConfig";

    /*
    @Retry 애너테이션이 붙은 메서드가 재시도 로직
    재시도를 구체적으로 어떻게 할지는 CONFIG_CLASS_NAME에 적힌 Config 클래스로 등록된 빈을 통해
    fallback 메서드에 적은 메서드가 바로 재시도를 했는데도 문제가 되면 호출해줄 메서드의 시그니처 등록
     */
    @Retry(name = CONFIG_CLASS_NAME, fallbackMethod = "fallback")
    public String process(String param){ // 재시도를 해주는 메서드
        return callAnotherServer(param);
    }
    
    private String fallback(String param, Exception e) {
        //retry에서 지정한 횟수를 모두 채우면 fallback이 호출됨
        System.out.println(param + "요청 처리 실패 한도를 초과하였습니다..");
        return "처리됨 : " + e.toString();
    }

    private String callAnotherServer(String param) {
        //retry exception은 retry 된다.
//        throw new RetryException("재시도 해주는 예외 발생 !!!");

        // ignore exception은 retry를 하지 않는다.
        throw new IgnoreException("재시도 안 해주는 예외 발생 !!!");
    }
}
