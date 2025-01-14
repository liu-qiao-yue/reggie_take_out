package com.itheima.reggie.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.stereotype.Service;

/**
 * @Author: ellie
 * @CreateTime: 2025-01-14
 * @Description: Elasticsearch通用业务类
 * @Version: 1.0
 */
@Service
public class ElasticsearchService {

    private final ElasticsearchClient esClient;

    public ElasticsearchService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }


}
