package com.fundoo.note.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundoo.note.model.Note;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ESService implements IESService {

    private final String INDEX = "fundoonote";
    private final String TYPE = "note";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @SuppressWarnings("unchecked")
    public String saveNote(Note newNote) throws IOException {
        Map<String, Object> document = objectMapper.convertValue(newNote, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(newNote.getNote_Id())).source(document);
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        return index.getResult().name();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String updateNote(Note note) throws IOException {
        Map<String, Object> document = objectMapper.convertValue(note, Map.class);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNote_Id())).doc(document);
        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        return update.getResult().name();
    }

    @Override
    public String deleteNote(Note note) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getNote_Id()));
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return delete.getResult().name();
    }

    @Override
    public List<Note> searchByTitle(String title, String email) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*" + title + "*")
                .analyzeWildcard(true).field("title").field("description"));
        searchSourceBuilder.query(queryBuilder);
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(search);
    }

    private List<Note> getSearchResult(SearchResponse search) {
        SearchHit[] hits = search.getHits().getHits();
        List<Note> list = new ArrayList<>();
        if (hits.length > 0) {
            Arrays.stream(hits)
                    .forEach(note -> list.add(objectMapper.convertValue(note.getSourceAsMap(), Note.class)));
        }
        return list;
    }
}
