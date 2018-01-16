package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions;

/**
 * Created by ups on 9/12/17.
 */
public class ERouteToAPI extends Exception {

    String uriAPI ;

    public ERouteToAPI(String uriAPI)  {
        super("Could not connect with the api " + uriAPI);
        this.uriAPI = uriAPI ;
    }

    public String getUriAPI() {
        return uriAPI;
    }

    public void setUriAPI(String uriAPI) {
        this.uriAPI = uriAPI;
    }
}
