package com.shdd.cfs.config;

import com.shdd.cfs.commit.iamp.IampRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetBean {
    @Value("${http.pool.maxTotal}")
    private Integer maxTotal;

    @Value("${http.pool.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.request.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.request.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.request.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.request.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;
    
    @Value("${http.request.uri.iamp}")
    private String iampURI;

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(
            @Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

    @Bean
    public CloseableHttpClient getCloseableHttpClient(
            @Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {
        return builder.build();
    }
    
    @Bean
    public IampRequest getIampRequest() {
        return new IampRequest(iampURI);
    }
}
