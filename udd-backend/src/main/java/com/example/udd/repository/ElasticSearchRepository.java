package com.example.udd.repository;

import com.example.udd.elasticsearch.entities.ApplicationIndex;
import com.example.udd.entities.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElasticSearchRepository extends ElasticsearchRepository<ApplicationIndex, String> {
}