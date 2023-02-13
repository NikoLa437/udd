package com.example.udd.elasticsearch.entities;

import com.example.udd.entities.Qualifications;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;

@Document(indexName = "application_index", shards = 1, replicas = 0)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationIndex {
    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private String firstName;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private String lastName;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private Qualifications qualifications;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private String cvContent;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private String coverLetterContent;

    @GeoPointField
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private GeoPoint location;

    @Field(type = FieldType.Text, analyzer = "serbian",searchAnalyzer = "serbian")
    private String city;
}
