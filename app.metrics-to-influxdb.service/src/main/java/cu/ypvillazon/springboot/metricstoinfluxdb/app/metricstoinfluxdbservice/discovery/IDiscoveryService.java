package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.discovery;

import java.net.URI;

/**
 * Created by ups on 14/01/18.
 */
public interface IDiscoveryService {

    public URI getUri(String serviceId) ;
}
