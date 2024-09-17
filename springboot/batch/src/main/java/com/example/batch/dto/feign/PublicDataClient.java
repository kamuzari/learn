package com.example.batch.dto.feign;

import com.example.batch.config.FeignConfig;
import com.example.batch.dto.PublicDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "publicDataClient",
        url = "https://apis.data.go.kr/B551011/KorService1",
        configuration = FeignConfig.class
)
public interface PublicDataClient {
    @GetMapping(value = "/areaBasedSyncList1", consumes = "application/json")
    PublicDataResponse getSpots(
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("_type") String type,
            @RequestParam("serviceKey")
            String serviceKey,
            @RequestParam("arrange")
            String order
            );
}
