package com.example.udd.services.impl;

import com.example.udd.elasticsearch.entities.ApplicationIndex;
import com.example.udd.entities.Application;
import com.example.udd.entities.DTO.ApplicationResponseDTO;
import com.example.udd.entities.DTO.Query;
import com.example.udd.entities.Operator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl {

    @Autowired
    private ElasticsearchRestTemplate elasticSearchRestTemplate;

    public List<ApplicationResponseDTO> complexSearch(List<Query> complexQuery) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        boolean isFirstElement = true;
        for(Query query : complexQuery) {
            if (query.Field == null || query.Value == null) {
                continue;
            }

            BoolQueryBuilder newQueryBuilder = QueryBuilders.boolQuery();

            if (isFirstElement) {
                newQueryBuilder.must(getQuery(query));
                isFirstElement=false;
            } else if (query.Operator == Operator.AND) {
                newQueryBuilder.must(getQuery(query));
                newQueryBuilder.must(queryBuilder);
            } else if (query.Operator == Operator.OR) {
                newQueryBuilder.should(getQuery(query));
                newQueryBuilder.should(queryBuilder);
            }

            queryBuilder = newQueryBuilder;
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<ApplicationIndex> searchHits = elasticSearchRestTemplate.search(searchQuery, ApplicationIndex.class);

        List<ApplicationResponseDTO> returnList = new ArrayList<>();

        for (SearchHit<ApplicationIndex> result : searchHits.getSearchHits()) {
            returnList.add(mapResultsToApplication(result));
        }

        return returnList;
    }

    private ApplicationResponseDTO mapResultsToApplication(SearchHit<ApplicationIndex> result) {
        ApplicationIndex application = result.getContent();
        return new ApplicationResponseDTO(application.getId(), application.getFirstName(), application.getLastName(), application.getQualifications(), application.getCity());
    }

    private QueryBuilder getQuery(Query query) {
        query.Field.trim();
        if(query.Value.startsWith("\"") && query.Value.endsWith("\"")){
            query.Value = query.Value.substring(1, query.Value.length()-1);
            return QueryBuilders.matchPhraseQuery(query.Field, query.Value);
        }else{
            return QueryBuilders.matchQuery(query.Field, query.Value);
        }
    }
}
