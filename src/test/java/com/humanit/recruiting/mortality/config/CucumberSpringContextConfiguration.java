package com.humanit.recruiting.mortality.config;

import com.humanit.recruiting.mortality.MortalityApplication;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = MortalityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberSpringContextConfiguration {
    private static final Logger Log = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);
    @Before
    public void setUp() {
        Log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
