package com.example.batch.reader;

import com.example.batch.dto.PublicDataResponse;
import com.example.batch.dto.feign.PublicDataClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;


public class PublicDataItemReader implements ItemReader<List<PublicDataResponse.Item>> {
    private static final Logger log = LoggerFactory.getLogger(PublicDataItemReader.class);
    private final String serviceKey;
    private final PublicDataClient publicDataClient;
    private final int numOfRows;
    private final String mobileOS;
    private final String mobileApp;
    private final String returnType;
    private String sort;
    private int pageNo;

    public PublicDataItemReader(PublicDataClient publicDataClient, String serviceKey, int page) {
        log.info("Creating a new PublicDataItemReader instance with pageNo: {}, instanceId: {}", pageNo, this.hashCode());
        this.publicDataClient = publicDataClient;
        this.serviceKey = serviceKey;
        this.returnType = "JSON";
        this.numOfRows = 200;
        this.mobileOS = "ETC";
        this.mobileApp = "atk";
        this.sort = "D";
        this.pageNo = page;
    }

    @Override
    public List<PublicDataResponse.Item> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.warn("============= PublicDataResponseReader pageNo {}", pageNo);
        PublicDataResponse response = publicDataClient.getSpots(
                numOfRows,
                pageNo,
                mobileOS,
                mobileApp,
                returnType,
                serviceKey,
                sort
        );

        if (response == null || response.getResponse() == null || response.getResponse().getBody() == null) {
            return null;
        }

        List<PublicDataResponse.Item> items = response.getResponse().getBody().getItems().getItem();
        if (isEnd(items))
            return null;
        this.pageNo = Integer.MAX_VALUE;
        log.error("============= PublicDataResponseReader END ==========");
        return items;
    }

    private boolean isEnd(List<PublicDataResponse.Item> items) {
        return items.isEmpty() || items == null;
    }
}