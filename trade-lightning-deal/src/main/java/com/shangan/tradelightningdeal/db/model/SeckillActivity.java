package com.shangan.tradelightningdeal.db.model;

import lombok.Data;

import java.util.Date;

@Data
public class SeckillActivity {
    private long id;
    private String activityName;
    private Long goodsId;
    private Date startTime;
    private Date endTime;
    private Integer availableStock;
    private Integer lockStock;
    private Integer activityStatus;
    private Integer seckillPrice;
    private Integer oldPrice;
    private Date createTime;
    public String getSeckillPriceInYuan() {
        return String.format("%.2f", this.seckillPrice / 100.0);
    }
    public String getOldPriceInYuan() {
        return String.format("%.2f", this.oldPrice / 100.0);
    }

}
