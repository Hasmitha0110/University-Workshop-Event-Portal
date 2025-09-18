package com.ruhuna.uniplus.bdd;

import com.ruhuna.uniplus.UniplusApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = UniplusApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CucumberSpringConfig {}
