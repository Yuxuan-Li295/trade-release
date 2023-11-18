package com.shangan.tradewebportal.service;

import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import com.shangan.tradewebportal.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StaticPageService {

    @Autowired
    private SeckillActivityService seckillActivityService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    public void createStaticPage(long seckillActivityId) {
        PrintWriter writer = null;
        try {
            //Query the necessary data for the seckill detail page
            SeckillActivity seckillActivity = seckillActivityService.querySeckillActivityById(seckillActivityId);
            Goods goodsInfo = goodsService.queryGoodsById(seckillActivity.getGoodsId());
            String newPrice = CommonUtils.changeF2Y(seckillActivity.getSeckillPrice());
            String oldPrice = CommonUtils.changeF2Y(seckillActivity.getOldPrice());

            //Storing the page data
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("seckillActivity",seckillActivity);
            resultMap.put("seckillPrice",newPrice);
            resultMap.put("oldPrice",oldPrice);
            resultMap.put("goods", goodsInfo);

            //Create context object thymeleaf
            Context context = new Context();
            //Put the data into context
            context.setVariables(resultMap);
            //Create the needed corresponding HTML page
            File file = new File("src/main/resources/templates/" + "seckill_item_" + seckillActivityId + ".html");
            writer = new PrintWriter(file);
            //Execute the template engine
            templateEngine.process("seckill_item", context, writer);
        } catch (Exception e) {
            log.error("Error creating static page for seckillActivity ID = {} : {}", seckillActivityId, e.getMessage());
        } finally {
            //Close the ouptut stream
            if (writer != null) {
                writer.close();
            }
        }
    }
}
