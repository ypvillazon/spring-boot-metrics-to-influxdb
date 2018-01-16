package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.restclient;

import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EHttpError;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EJsonBad;

/**
 * Created by ups on 17/12/17.
 */
public interface IRestClient {

    public <T> T get(String uri, Class<T> responseType) throws EJsonBad, EHttpError;

}
