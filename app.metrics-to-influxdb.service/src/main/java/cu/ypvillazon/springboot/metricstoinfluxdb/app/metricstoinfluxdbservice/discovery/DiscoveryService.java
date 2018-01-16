package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by ups on 14/01/18.
 */

@Service
public class DiscoveryService implements IDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    private HashMap<String, ServiceInstance> poolServiceInstance ;

    public DiscoveryService() {
        poolServiceInstance = new HashMap<>() ;
    }

    @Override
    public URI getUri(String serviceId){
        ServiceInstance serviceInstance = null ;
        if(poolServiceInstance.containsKey(serviceId)){
            logger.info("Get "+serviceId+" from pool.");
            serviceInstance = poolServiceInstance.get(serviceId) ;
        } else {
            logger.info("Checking if "+serviceId+" is available.");
            if(discoveryClient.getInstances(serviceId).size() > 0){
                logger.info("Add "+serviceId+" to pool.");
                serviceInstance = discoveryClient.getInstances(serviceId).get(0) ;
                poolServiceInstance.put(serviceId,serviceInstance) ;
            } else {
                logger.info("the "+serviceId+" is not available.");
                serviceInstance = null ;
            }
        }
        if(serviceInstance!=null){
            return serviceInstance.getUri() ;
        } else {
            return null ;
        }
    }

    @Scheduled(cron = "5 */5 * * * *")
    private void refreshConnectionWithDiscoveryServer(){
        logger.info("Destroy pool of service instance. Refresh connection with discovery server.");
        this.poolServiceInstance = new HashMap<>() ;
    }



}
