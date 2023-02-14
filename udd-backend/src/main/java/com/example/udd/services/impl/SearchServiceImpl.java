package com.example.udd.services.impl;

import com.example.udd.elasticsearch.entities.ApplicationIndex;
import com.example.udd.entities.City;
import com.example.udd.entities.DTO.ApplicationResponseDTO;
import com.example.udd.entities.DTO.GeoLocationSearchDTO;
import com.example.udd.entities.DTO.Query;
import com.example.udd.entities.Operator;
import com.example.udd.repository.CityRepository;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static co.elastic.clients.elasticsearch._types.SortOptions.Kind.GeoDistance;

@Service
public class SearchServiceImpl {

    @Autowired
    private ElasticsearchRestTemplate elasticSearchRestTemplate;

    @Autowired
    private CityRepository cityRepository;

    public List<ApplicationResponseDTO> complexSearch(List<Query> complexQuery) {
        BoolQueryBuilder queryBuilder = this.createQueryBuilder(complexQuery);
        HighlightBuilder highlightBuilder = this.createHighlightBuilder(complexQuery);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .build();

        SearchHits<ApplicationIndex> searchHits = elasticSearchRestTemplate.search(searchQuery, ApplicationIndex.class);

        return generateReturnListFromSearchHits(searchHits);
    }

    public List<ApplicationResponseDTO> geoLocationSearch(GeoLocationSearchDTO geoLocationSearchDTO) {
        City city = cityRepository.getById(geoLocationSearchDTO.CityId);
        QueryBuilder queryBuilder = QueryBuilders.geoDistanceQuery("location")
                .point(city.getLatitude(), city.getLongitude())
                .distance(geoLocationSearchDTO.Radius, DistanceUnit.KILOMETERS);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<ApplicationIndex> searchHits = elasticSearchRestTemplate.search(searchQuery, ApplicationIndex.class);

        return generateReturnListFromSearchHits(searchHits);
    }

    private List<ApplicationResponseDTO> generateReturnListFromSearchHits(SearchHits<ApplicationIndex> searchHits) {
        List<ApplicationResponseDTO> returnList = new ArrayList<>();

        for (SearchHit<ApplicationIndex> result : searchHits.getSearchHits()) {
            String highlight = createHighlightStringFromResults(result.getHighlightFields());

            returnList.add(mapResultsToApplication(result, highlight));
        }

        return returnList;
    }

    private String createHighlightStringFromResults(Map<String, List<String>> highlightFieldsMap) {
        String retVal = "";

        if(highlightFieldsMap.get("firstName") != null){
            retVal += highlightFieldsMap.get("firstName").get(0) + "... ";
        }

        if(highlightFieldsMap.get("lastName") != null){
            retVal += highlightFieldsMap.get("lastName").get(0) + "... ";
        }

        if(highlightFieldsMap.get("qualifications") != null){
            retVal += highlightFieldsMap.get("qualifications").get(0) + "... ";
        }

        if(highlightFieldsMap.get("cvContent") != null){
            for (String cvHighlight :  highlightFieldsMap.get("cvContent")) {
                retVal += cvHighlight + "... ";
            }
        }

        if(highlightFieldsMap.get("coverLetterContent") != null){
            for (String clHighlight :  highlightFieldsMap.get("coverLetterContent")) {
                retVal += clHighlight + "... ";
            }
        }

        return retVal;
    }

    private HighlightBuilder createHighlightBuilder(List<Query> complexQuery){
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        for(Query query : complexQuery) {
            if (query.Field.equals("firstName"))
                highlightBuilder.field("firstName").preTags("<b>").postTags("</b>");
            else if (query.Field.equals("lastName"))
                highlightBuilder.field("lastName").preTags("<b>").postTags("</b>");
            else if (query.Field.equals("qualifications"))
                highlightBuilder.field("qualifications").preTags("<b>").postTags("</b>");
            else if (query.Field.equals("cvContent"))
                highlightBuilder.field("cvContent").preTags("<b>").postTags("</b>");
            else if (query.Field.equals("coverLetterContent"))
                highlightBuilder.field("coverLetterContent").preTags("<b>").postTags("</b>");
        }
        return highlightBuilder;
    }
    private BoolQueryBuilder createQueryBuilder(List<Query> complexQuery){
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

        return queryBuilder;
    }

    private ApplicationResponseDTO mapResultsToApplication(SearchHit<ApplicationIndex> result, String highlight) {
        ApplicationIndex application = result.getContent();
        return new ApplicationResponseDTO(application.getId(), application.getFirstName(), application.getLastName(), application.getQualifications(), application.getCity(), highlight);
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
