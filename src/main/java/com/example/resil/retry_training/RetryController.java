package com.example.resil.retry_training;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("retry")
@RequiredArgsConstructor
public class RetryController {

    private final RetryService retryService;

    @GetMapping("call/{param}")
    public String call(@PathVariable String param){
        return retryService.process(param);
    }
}
