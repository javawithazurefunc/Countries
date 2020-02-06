package Countries.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;

import Data.Countries;

import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class CountriesFuncApp {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke function deployed to Azure.
     * More details: https://aka.ms/functions_authorization_keys
     */
    @FunctionName("GetCountriesList")
    public HttpResponseMessage GetCountriesList(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        Map<Integer, String> countriesList = new Countries().GetCountriesList();
        return request.createResponseBuilder(HttpStatus.OK).body(countriesList).build();
    }

    @FunctionName("IsCountryValid")
    public HttpResponseMessage IsCountryValid(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query).replace("'", "");

        Map<Integer, String> countriesList = new Countries().GetCountriesList();

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(IsCountryValid(name, countriesList)).build();
        }
    }

    private boolean IsCountryValid(String name, Map<Integer, String> list)
    {
        for (String string : list.values()) {
            if (string.equals(name))
            {
                return true;
            }
        }

        return false;
    }
}
