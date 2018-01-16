package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions;

/**
 * Created by ups on 9/12/17.
 */
public class EJsonBad extends Exception {

    public EJsonBad(String msg)  {
        super(msg);
    }
}
