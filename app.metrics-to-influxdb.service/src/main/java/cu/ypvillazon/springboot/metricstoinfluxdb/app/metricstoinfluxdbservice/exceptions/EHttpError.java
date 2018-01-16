package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions;

/**
 * Created by ups on 9/12/17.
 */
public class EHttpError extends Exception {

    HttpClientError clientError ;

    public EHttpError(HttpClientError clientError)  {
        super(clientError.getMessageDetails());
        this.clientError = clientError ;
    }

    public HttpClientError getClientError() {
        return clientError;
    }

    public void setClientError(HttpClientError clientError) {
        this.clientError = clientError;
    }
}
