package com.carrefour.kata;

import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;

@Cucumber
@SelectClasspathResource("com/carrefour/kata")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.carrefour.kata.steps")
public class CucumberOrderTest {
}
