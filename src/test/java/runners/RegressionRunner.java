package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        glue = {"steps"},
        tags = {"@api_scen9"},
        plugin = {"pretty", "html:target/reports","json:target/cucumber.json"}, //is responsible for creating(generating) junit reports in json format
        //which is needed for cucumber reports to create the statistics of the tests.
        //html -is a face of the webpage. it's used for creating webpages. static webpage.
        dryRun = false
)

public class RegressionRunner {
}
