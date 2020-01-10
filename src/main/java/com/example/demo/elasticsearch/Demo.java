package com.example.demo.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class Demo {
    @Test
    public void testEs2() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.253.128", 9200, "http")));
        IndexRequest request = new IndexRequest(
                "gmall");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        try{
            client.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testEs1() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.253.128", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name", "red"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] hitsHits = hits.getHits();
        for (SearchHit hit : hitsHits) {
            System.out.println(hit.getSourceAsString());
        }
        try{
            client.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testEs() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.253.128", 9200, "http")));
        GetRequest request = new GetRequest(
                "move_index",
                "3");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String sourceAsString = response.getSourceAsString();
        System.out.println(sourceAsString);
        String name = response.getField("name").getValue();
        System.out.println(name);
        try{
            client.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
