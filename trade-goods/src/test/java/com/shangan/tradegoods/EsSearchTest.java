package com.shangan.tradegoods;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.model.Goods;
import org.apache.http.HttpHost;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 我的步骤：
 * 1. 在pom.xml中添加必要的依赖
 * 2. 在EsSearchTest 类中，编写方法来测试连接与搜索
 *   - 使用@Test注解（Junit框架的标准方式进行单元测试）
 * RestHighLevelClient的API是ElasticSearch推荐的客户端组件，
 * 其封装系统操作ES的方法，包括索引结构管理，数据增删改查管理，常用查询方法，
 * 并且可以结合原生ES查询原生语法
 */

/**
 * @SpringbootTest 在默认情况下开始在测试的当期包中搜索，然后在包结构中向上搜索，寻找用@Configuration注解的类
 * 从中读取配置以创建应用程序上下文。
 */
@SpringBootTest
public class EsSearchTest {


    @Test
    public void contexLoads() {

    }
    @Autowired
    private RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
            new HttpHost("127.0.0.1", 9200, "http")
    ));

    @Test
    public void esTest() {
        System.out.println(JSON.toJSONString(client));
    }


    @Test
    public void addGoodsDoc() throws Exception {
        Goods goods = new Goods();
        goods.setProductId("G12349");
        goods.setProductName("Ice Cream");
        goods.setCategory("Foods");
        goods.setPrice(9.99);
        goods.setDescription("Yuxuan's snack");
        // Null check for the goods object
        if (goods == null) {
            System.out.println("Error: Goods object is null.");
            return;
        }
        // Convert object to JSON
        String data = JSON.toJSONString(goods);
        IndexRequest request = new IndexRequest("goodthing").id(goods.getProductId()).source(data, XContentType.JSON);
        // Null check for the IndexRequest
        if (request == null) {
            System.out.println("Error: IndexRequest is null.");
            return;
        }
        // Null check for the client
        if (client == null) {
            System.out.println("Error: RestHighLevelClient (client) is null.");
            return;
        }
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());
        RefreshRequest refreshRequest = new RefreshRequest("goods");
        client.indices().refresh(refreshRequest, RequestOptions.DEFAULT);
    }

    /*
    Match all goods
     */
    @Test
    public void retrieveAllGoods() {
        try {
            //Fetch all goods from Elasticsearch
            List<Goods> goodsList = fetchAllGoodsFromElasticsearch();
            //Display the total number of records
            System.out.println("总记录数" + goodsList.size());
            //Display each goods entry
            goodsList.forEach((goods -> System.out.println(goods.toString())));
        } catch (Exception e) {
            System.err.println("Failed to retrieve goods from Elasticsearch: " + e.getMessage());
        }
    }

    private List<Goods> fetchAllGoodsFromElasticsearch() throws Exception {
        //Construct a search source builder with a match-all query
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(50); //fetch up to 2 records; adjust as needed
        //Execute the search using the client and retrieve the response
        SearchResponse response = client.search(new SearchRequest("goodthing").source(builder), RequestOptions.DEFAULT);
        //Convert the search hits into a list of Goods objects
        return Stream.of(response.getHits().getHits())
                .map(hit -> {
                    //Parse hit's source as a JSON string into a Goods object
                    return JSON.parseObject(hit.getSourceAsString(), Goods.class);
                })
                .collect(Collectors.toList());
    }

    /*
    Term search for Goods based on category
     */
    @Test
    public void goodsTermQuerySearch() throws IOException {
        //Create a request targeting the 'goods' index
        SearchRequest searchRequest = new SearchRequest("goodthing");
        /*
         * Use term query to search for a specific category
         */
        TermQueryBuilder query = QueryBuilders.termQuery("category", "electronics");
        //Attach query and pagination details to the request
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(query)
                .from(0)
                .size(50);
        searchRequest.source(sourceBuilder);
        //Execute the search
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //Display total records and results
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("总记录数:" + searchHits.getTotalHits().value);
        List<Goods> goodsList = new ArrayList<>();
        searchHits.forEach(hit -> {
            String json = hit.getSourceAsString();
            Goods goodsItem = JSON.parseObject(json, Goods.class);
            goodsList.add(goodsItem);
        });
        System.out.println(JSON.toJSONString(goodsList));
    }

    /*
     * Match Query for Goods based on the Description
     */
    @Test
    public void goodsDescriptionMatchQuery() throws IOException {
        //Create a request targeting the 'goods' index
        SearchRequest searchRequest = new SearchRequest("goodthing");
        /*
         * Use MatchQuery to search for items with descriptions
         * containing the "wireless"
         */
        MatchQueryBuilder query = QueryBuilders.matchQuery("description","wireless");
        //Attach query and pagination details to the request
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(query)
                .from(0)
                .size(50);
        searchRequest.source(sourceBuilder);
        //Execute the search
        SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        //Display total records and results
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("总记录数" + searchHits.getTotalHits().value);
        List<Goods> goodsList = new ArrayList<>();
        searchHits.forEach(hit -> {
            String json = hit.getSourceAsString();
            Goods goodsItem = JSON.parseObject(json, Goods.class);
            goodsList.add(goodsItem);
        });
        //Print the names of the match items
        System.out.println("Matched Goods:");
        goodsList.forEach(g -> {
            System.out.println(g.getProductName());
        });
    }

    @Test
    public void goodsDescriptionQueryStringQuery() throws IOException {
        //Set up(similar to the previous procedure
        SearchRequest searchRequest = new SearchRequest("goodthing");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //Create a queryString query for 'description' and 'productName'
        QueryStringQueryBuilder queryStringQuery = QueryBuilders.queryStringQuery("wireless OR 13")
                .field("description")
                .field("productName")
                .defaultOperator(Operator.OR);
        //Apply Query and Pagination
        sourceBuilder.query(queryStringQuery).from(0).size(50);
        searchRequest.source(sourceBuilder);
        //Execute Search
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        //Processing the result
        System.out.println("Total number of records: " + searchHits.getTotalHits().value);
        List<Goods> goodsList = new ArrayList<>();
        searchHits.forEach(hit -> {
            Goods goodsItem = JSON.parseObject(hit.getSourceAsString(), Goods.class);
            goodsList.add(goodsItem);
        });
        //Print results
        System.out.println("Match Goods:");
        goodsList.forEach(g -> {
            System.out.println(g.getProductId() + " - " + g.getProductName() + " - " + g.getDescription());
        });
    }

    /**
     * Search the proucts with some advanced searching features with a specific
     * description or productName
     */
    @Test
    public void searchGoodThing() throws IOException {
        //Creat search request and construct search conditions
        SearchRequest request = new SearchRequest("goodthing");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //Create the wildcard Queries
        WildcardQueryBuilder descQuery = QueryBuilders.wildcardQuery("description", "wir*");
        WildcardQueryBuilder nameQuery = QueryBuilders.wildcardQuery("productName", "iph*");
        //Combine queries using a boolean OR condition
        BoolQueryBuilder combineQuery = QueryBuilders.boolQuery();
        combineQuery.should(descQuery);
        combineQuery.should(nameQuery);
        //Apply query and pagination
        sourceBuilder.query(combineQuery);
        sourceBuilder.from(0).size(5);
        request.source(sourceBuilder);
        //Execute the search and process the results
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
        long totalHits = response.getHits().getTotalHits().value;
        System.out.println("Total Results: " + totalHits);
        List<Goods> resultList = Arrays.stream(response.getHits().getHits())
                .map(hit -> JSON.parseObject(hit.getSourceAsString(),Goods.class))
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(resultList));
    }

    //More examples write by myself
    private void executeSearchAndPrintResults(SearchRequest request, QueryBuilder query) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(query);
        sourceBuilder.from(0).size(10);
        request.source(sourceBuilder);
        //Execute the path
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //process the results
        long totalHits = response.getHits().getTotalHits().value;
        System.out.println("Total match results: " + totalHits);
        List<Goods> resultList = Arrays.stream(response.getHits().getHits())
                .map(hit->JSON.parseObject(hit.getSourceAsString(),Goods.class))
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(resultList));
    }

    //Single Match Query
    @Test
    public void singleMatchQuery() throws IOException {
        SearchRequest request = new SearchRequest("goodthing");
        QueryBuilder query = QueryBuilders.matchQuery("productName", "ice cream");
        executeSearchAndPrintResults(request,query);
    }

    // Multi-Match Query
    @Test
    public void multiMatchQuery() throws IOException {
        SearchRequest request = new SearchRequest("goodthing");
        QueryBuilder query = QueryBuilders.multiMatchQuery("wireless iPhone","description", "productName");
        executeSearchAndPrintResults(request, query);
    }
}
