package com.shangan.tradecommon.model;

import lombok.Data;

import java.io.Serializable;

/*
Implements serializable, the object in this class can be serializable as needed
 */
@Data
public class TradeResultDTO<T> implements Serializable {

    private int code;

    //Error hint message
    private String errorMessage;

    //Return data
    private T data;


}
