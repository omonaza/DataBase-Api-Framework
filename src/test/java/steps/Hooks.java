package steps;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import io.restassured.RestAssured;
import utils.config.Config;
import utils.db.DataBaseUtils;

public class Hooks {

    //Implement DB Connection Steps here
    private static boolean isExecuted = false;

    public static Scenario scenarioInfo;

    @Before
    public void testExecutionSetup(Scenario scenario) throws Exception {
        scenarioInfo = scenario;

//        System.out.println("tag: "+scenario.getSourceTagNames());//it will print current tag you run
//        boolean isToRun  = scenario.getSourceTagNames().toString().contains("api_");
//
//        if(isToRun) {
//               System.out.println("I want to run API");
//           }else{
//            System.out.println("Run DB method connection");
//        }

        getEnvironment();

        if (!isExecuted) {
            DataBaseUtils.connectToDatabase();
//            RestAssured.baseURI = Config.getPropertiesValue("food_delivery_base_url");
            isExecuted = true;
        }
    }



    public static String getEnvironment() {
        String systemPropeties = System.getProperty("environment");
        if (null == systemPropeties) {
            systemPropeties = Config.getPropertiesValue("environment");
        }
        return systemPropeties;
    }
}
