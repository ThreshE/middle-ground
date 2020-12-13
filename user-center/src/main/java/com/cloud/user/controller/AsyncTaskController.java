package com.cloud.user.controller;

import com.cloud.user.service.ThreadTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AsyncTaskController {
    @Autowired
    private ThreadTasks tasks;

    @GetMapping("/users-anon/useTask")
    public String useSyncTask() throws InterruptedException, ExecutionException {
        Future<String> future = tasks.startTask();
        return future.get();
    }
}
