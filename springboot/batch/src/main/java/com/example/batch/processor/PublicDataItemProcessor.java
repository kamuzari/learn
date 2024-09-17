package com.example.batch.processor;

import com.example.batch.domain.Category;
import com.example.batch.domain.Spot;
import com.example.batch.dto.PublicDataResponse.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class PublicDataItemProcessor implements ItemProcessor<List<Item>, List<Spot>> {
    private static final Logger log = LoggerFactory.getLogger(PublicDataItemProcessor.class);

    @Override
    public List<Spot> process(List<Item> items) throws Exception {
        log.info("Creating a new PublicDataItemReader instance with instanceId: {}", this.hashCode());
        log.info("Before Convert response From exterior api: {}", items);
        return items.stream().map(item ->
                new Spot(
                        item.getTitle(),
                        Integer.parseInt(item.getContentid()),
                        item.getAddr1() + "" + item.getAddr2(),
                        item.getMapx(),
                        item.getMapy(),
                        item.getFirstimage(),
                        item.getFirstimage2(),
                        Category.find(Integer.parseInt(item.getContenttypeid())).name()
                )
        ).toList();
    }
}
