package com.sw.test.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {


   Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLog(){


       logger.info("这是一个日志");
   }
}
