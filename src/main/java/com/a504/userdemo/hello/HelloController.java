package com.a504.userdemo.hello;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello-world/string")
    @ResponseBody
    public String helloWorldString() {
        return "hello";
    }

    @GetMapping("/hello-world/json")
    @ResponseBody
    public Hello helloWorldJson() {
        Hello hello = new Hello();
        hello.setMessage("Hello");
        return hello;
    }

    @GetMapping("/hello-world/page")
    public String helloWorldPage() {
        return "HelloWorld";
    }

    @Getter
    @Setter
    public static class Hello {
        private String message;
    }
}
