package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.configuration;

/**
 * Created by ups on 14/01/18.
 */

public class Service {
    String name;
    String description;
    String serviceId;

    public Service(String name, String description, String serviceId) {
        this.name = name;
        this.description = description;
        this.serviceId = serviceId;
    }

    public Service() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
