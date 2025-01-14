package com.itheima;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.itheima.reggie.ReggieApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @Author: ellie
 * @CreateTime: 2025-01-14
 * @Description: es8 测试类，https://www.cnblogs.com/konghuanxi/p/18094055
 * @Version: 1.0
 */
@Slf4j
@SpringBootTest(classes = ReggieApplication.class)
@RunWith(SpringRunner.class)
public class ElasticTest {

    @Autowired
    private ElasticsearchClient esClient;

    @Test
    public void indexExists() throws IOException {
        // 索引名字
        String indexName = "kuang";
        BooleanResponse books = esClient.indices().exists(e -> e.index(indexName));
        assertTrue(books.value());
    }

    /**
     * 创建索引和规则
     * @throws IOException
     */
    @Test
    public void addIndex() throws IOException {
        esClient.indices().create(c->c
                .index("sprinboot-test")
                .mappings(m -> m
                        // name 为 text 类型，但不索引此字段，
                        // 将 index 设置为 false 时，你告诉 Elasticsearch 不要为这个字段创建这样的索引，因此你不能直接对该字段执行搜索查询、过滤或排序操作。但是，你仍然可以在文档中存储和检索这个字段的值。
                        .properties("name", p->p
                                .text(t->t.index(false))
                        )
                        .properties("age", p->p
                                .long_(t->t))
                )
        );
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        esClient.indices().delete(d->d.index("sprinboot-test"));
    }
}
