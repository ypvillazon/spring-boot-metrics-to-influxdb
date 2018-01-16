package cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EHttpError;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.EJsonBad;
import cu.ypvillazon.springboot.metricstoinfluxdb.app.metricstoinfluxdbservice.exceptions.HttpClientError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.NoRouteToHostException;

/**
 * Created by ups on 17/12/17.
 */

@Service
public class RestClient implements IRestClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    RestTemplate restTemplate ;

    public RestClient() {
        restTemplate = new RestTemplate() ;
    }

    @Override
    public <T> T get(String uri, Class<T> responseType) throws EJsonBad, EHttpError  {
        ResponseEntity<String> responseEntity = null ;
        T result = null;
        try {
            responseEntity = restTemplate.getForEntity(uri,String.class) ;
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                logger.info("Successful response code: " + responseEntity.getStatusCode() +" on " + uri);
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(responseEntity.getBody(),responseType);
            }
        } catch (NoRouteToHostException e) {
            throw new EHttpError(new HttpClientError(404,"Could not connect with the api"+uri,"Could not connect with the api"+uri)) ;
        } catch (IOException e) {
            logger.error("Bad json from " +uri+ ", body readed "+responseEntity.getBody()+", details:"+e.getMessage());
            throw new EJsonBad("Bad json from " +uri+ ", details:"+e.getMessage()) ;
        } catch (HttpClientErrorException e) {
            throw new EHttpError(new HttpClientError(e.getStatusCode().value(),e.getStatusText(),e.getMessage())) ;
        } catch (RestClientException e) {
            if(e.getMessage().contains("Host unreachable")){
                throw new EHttpError(new HttpClientError(404,"Could not connect with the api"+uri,"Could not connect with the api"+uri)) ;
            }
        }
        return result ;
    }




}
