package com.freedommuskrats;

import com.freedommuskrats.annotations.EnablePickGraph;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnablePickGraph
public class test {

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(test.class)
                .build()
                .run(args)
        ;
    }
}
