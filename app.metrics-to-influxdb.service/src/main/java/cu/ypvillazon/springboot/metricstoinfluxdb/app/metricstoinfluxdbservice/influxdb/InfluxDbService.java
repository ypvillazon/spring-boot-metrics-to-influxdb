package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ups on 13/01/18.
 */

@Service
public class InfluxDbService implements IInfluxDbService{

    @Value("${infludb.server.host}")
    private String host ;

    @Value("${infludb.server.port}")
    private String port ;

    @Value("${infludb.server.dbname}")
    private String dbname ;

    @Value("${infludb.server.username}")
    private String username ;

    @Value("${infludb.server.password}")
    private String password ;

    @Value("${infludb.server.retentionPolicy}")
    private String retentionPolicy ;

    InfluxDB influxDB ;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public InfluxDbService() { }


    @PostConstruct
    public void initConnection(){
        logger.info("Initializing connection with influxdb http://"+host+":"+port+"?dbname="+dbname);
        influxDB = InfluxDBFactory.connect("http://"+host+":"+port, username, password);
        influxDB.createDatabase(dbname);
        influxDB.setDatabase(dbname);
        influxDB.setRetentionPolicy(retentionPolicy);
        influxDB.enableBatch(10, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void writeMetrics(HashMap<String, String> tags, HashMap<String, Object> fields){
        BatchPoints batchPoints = BatchPoints
                .database(dbname)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        Point point = Point.measurement("microservice_status")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag(tags)
                .fields(fields)
                .build();
        logger.info("Write metrics:: tags: " + getTags(tags) + " fields: " + getFields(fields));
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    private String getTags(HashMap<String, String> tags){
        String tags_ = "" ;
        Set set = tags.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            tags_ = tags_ + " " + mentry.getKey()+ ": " + mentry.getValue() ;
        }
        return tags_ ;
    }

    private String getFields(HashMap<String, Object> fields){
        String fields_ = "" ;
        Set set = fields.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            fields_ = fields_ + " " + mentry.getKey()+ ": " + mentry.getValue() ;
        }
        return fields_ ;
    }


}
