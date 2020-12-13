package com.cloud.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class ThreadTasks {
    /**
     * 如果异步方法有返回值，一定要使用Future包装，否则无法返回
     * @return
     * @throws InterruptedException
     */
    @Async
    public Future<String> startTask() throws InterruptedException {
        Thread.sleep(3000);
        log.info("this is async task");
        return new AsyncResult<>("123456");
    }
}
