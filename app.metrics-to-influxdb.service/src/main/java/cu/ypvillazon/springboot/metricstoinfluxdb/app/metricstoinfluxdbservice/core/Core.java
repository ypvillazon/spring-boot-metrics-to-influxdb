package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.core;

import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.configuration.Applications;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.configuration.Service;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.discovery.IDiscoveryService;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EHttpError;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EJsonBad;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.influxdb.IInfluxDbService;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.restclient.IRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;
import java.util.*;

/**
 * Created by ups on 14/01/18.
 */

@org.springframework.stereotype.Service
public class Core  {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${infludb.metrics}")
    private String[] metricsToMonitor ;

    @Autowired
    private IInfluxDbService influxDbService ;

    @Autowired
    private IDiscoveryService iDiscoveryService ;

    @Autowired
    private IRestClient iRestClient ;


    private Applications applications;

    @Autowired
    public void setApp(Applications applications) {
        this.applications = applications;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void run(){
        if(applications.getServices() != null){
            for (Service service: applications.getServices()){
                URI uri = iDiscoveryService.getUri(service.getServiceId()) ;
                if(uri != null){
                    HashMap<String,String> tags = new HashMap<>() ;
                    tags.put("serviceId",service.getServiceId()) ;
                    tags.put("serviceName",service.getName()) ;
                    tags.put("serviceDescription",service.getDescription()) ;

                    HashMap<String,Object> fields = new HashMap<>() ;
                    try {
                        HashMap<String, Long> metrics = iRestClient.get(uri + "/metrics",HashMap.class) ;
                        if(metrics != null && metrics.size()>0){
                            Set set = metrics.entrySet() ;
                            Iterator iterator = set.iterator();
                            while(iterator.hasNext()) {
                                Map.Entry mentry = (Map.Entry)iterator.next();
                                if(Arrays.asList(metricsToMonitor).contains(mentry.getKey())){
                                    fields.put((String) mentry.getKey(), mentry.getValue()) ;
                                }
                            }
                        }
                        influxDbService.writeMetrics(tags,fields);
                    } catch (EHttpError eHttpError) {
                        logger.error(eHttpError.getMessage());
                    } catch (EJsonBad eJsonBad) {
                        logger.error(eJsonBad.getMessage());
                    }
                }
            }
        }
    }
}
