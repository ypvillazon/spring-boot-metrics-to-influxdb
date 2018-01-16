package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions;


/**
 * Created by mason on 15/08/17.
 */
public class ENotFound extends Exception {

    public ENotFound(String msg) {
        super(msg);
    }

}
