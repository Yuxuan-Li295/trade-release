package com.shangan.tradegoods.service.impl;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient esClient;


    @Override
    public void addGoodsToES(Goods goods) {
        try {
            if (goods == null) {
                log.warn("Goods object is null. Aborting add to ES.");
                return;
            }
            if (esClient == null) {
                log.error("ES Client is null. Aborting add to ES.");
                return;
            }
            //Convert Goods object to JSON string
            String jsonData = JSON.toJSONString(goods);
            IndexRequest request = new IndexRequest("goods").source(jsonData, XContentType.JSON);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            log.info("Goods added to ES: {}, {}", jsonData, response);
        } catch (IOException e) {
            log.error("Error while adding goods to ES", e);
        }
    }




    @Override
    public List<Goods> searchGoodsList(String keyword, int from, int size) {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //Create a bool query to combine our match queries
        /*

          1. {
             "title": "apple fruit",
             "description": "apple is a delicious fruit"
             }

          2. {
             "title": "apple computer",
             "description": "apple computer is popular"
            }

          3. {
             "title": "orange fruit",
             "description": "apple is used in apple pie"
            }
         */
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", keyword).boost(2.0f)) //Double the weight for  the title
                .should(QueryBuilders.matchQuery("description", keyword))
                .should(QueryBuilders.matchQuery("keywords", keyword));

        sourceBuilder.query(boolQuery)
                .from(from)
                .size(size);
        //Setting sorting rule to sort by crateTime in descending order
        sourceBuilder.sort("createTime", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        List<Goods> goodsList = new ArrayList<>();
        try {
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            searchResponse.getHits().forEach(hit -> {
                String sourceAsString = hit.getSourceAsString();
                Goods goods = JSON.parseObject(sourceAsString, Goods.class);
                goodsList.add(goods);
            });
        } catch (IOException e) {
            log.error("SearchService Error(searchGoodsList");
        }
        return goodsList;
    }
}
