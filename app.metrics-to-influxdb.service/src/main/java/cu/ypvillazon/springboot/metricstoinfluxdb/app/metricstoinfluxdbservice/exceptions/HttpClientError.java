package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions;

/**
 * Created by ups on 20/12/17.
 */
public class HttpClientError {

    private int statusCode ;
    private String statusText ;
    private String messageDetails ;

    public HttpClientError(int statusCode, String statusText, String messageDetails) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.messageDetails = messageDetails;
    }

    public HttpClientError() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }
}
