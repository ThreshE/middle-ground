package com.cloud.user.controller;

import com.cloud.user.config.NameConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RefreshScope
public class TestController {
    @Autowired
    private NameConf nameConf;

    @GetMapping("/shownames")
    public List<String> showNames() {
        List<String> skills = new ArrayList<>();
        Stream.of(nameConf.getName()).forEach(skills::add);
        return skills;
    }
}
