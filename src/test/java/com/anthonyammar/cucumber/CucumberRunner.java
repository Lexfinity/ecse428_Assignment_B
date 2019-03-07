package test.java.com.anthonyammar.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = {"features"})
public class CucumberRunner {
}
