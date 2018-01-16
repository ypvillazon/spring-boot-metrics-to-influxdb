package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ups on 13/01/18.
 */

@Component
@ConfigurationProperties(prefix = "application")
public class Applications {
    List<Service> services ;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
