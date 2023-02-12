package com.example.udd.elasticsearch.config;

import com.example.udd.elasticsearch.entities.ApplicationIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

import javax.annotation.PostConstruct;

@Configuration
public class ESConfig {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostConstruct
    public void init() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ApplicationIndex.class);
        indexOperations.putMapping(indexOperations.createMapping());
        indexOperations.refresh();
    }
}
