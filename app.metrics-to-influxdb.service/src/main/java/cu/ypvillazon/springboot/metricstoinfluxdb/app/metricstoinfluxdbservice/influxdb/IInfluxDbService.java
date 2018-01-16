package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.influxdb;

import java.util.HashMap;

/**
 * Created by ups on 14/01/18.
 */
public interface IInfluxDbService {

    public void writeMetrics(HashMap<String, String> tags, HashMap<String, Object> fields) ;
}
