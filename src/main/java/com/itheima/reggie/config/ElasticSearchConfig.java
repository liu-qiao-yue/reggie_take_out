package com.itheima.reggie.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * @Author: ellie
 * @CreateTime: 2025-01-14
 * @Description: ElasticSearchConfig 配置类
 * @Version: 1.0
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "es")
public class ElasticSearchConfig {

    @Setter
    private String serverUrl;

    @Setter
    private String username;

    @Setter
    private String password;


    @Bean(name = "esClient")
    public ElasticsearchClient esClient() {
        ElasticsearchTransport transport = getElasticsearchTransport(username, password,toHttpHost());
        return new ElasticsearchClient(transport);
    }

    private HttpHost[] toHttpHost() {
        if (!StringUtils.hasLength(serverUrl)) {
            throw new RuntimeException("invalid elasticsearch configuration");
        }

        String[] hostArray = serverUrl.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        HttpHost httpHost;
        for (int i = 0; i < hostArray.length; i++) {
            String[] strings = hostArray[i].split(":");
            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
            httpHosts[i] = httpHost;
        }

        return httpHosts;
    }

    private static ElasticsearchTransport getElasticsearchTransport(String username, String passwd, HttpHost...hosts) {
        // 账户密码的配置
        final CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, passwd)
        );

        // 创建 RestClient 对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(hc->hc.setDefaultCredentialsProvider(provider))
                .build();

        return new RestClientTransport(client, new JacksonJsonpMapper());

    }
}
