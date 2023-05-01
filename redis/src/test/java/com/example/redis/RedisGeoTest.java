package com.example.redis;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.data.redis.domain.geo.Metrics;

@SpringBootTest
public class RedisGeoTest {

	@Container
	static final GenericContainer<?> redis = new GenericContainer<>(
		DockerImageName.parse("redis:latest")).withExposedPorts(6379);

	static {
		redis.start();
		System.setProperty("spring.redis.host", redis.getHost());
		System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	@DisplayName("geo 데이터 반경 검색")
	void testGeoInsert() {

		Location 판교_현대_백화점 = Location.create("판교 현대 백화점", "37.392247,127.1116781");
		Location 판교_중앙_공원 = Location.create("판교 중앙 공원", "37.3783136,127.1237345");
		Location 판교_울세_공원 = Location.create("판교 울세 공원", "37.3843079,127.117645");
		Location 판교_울세_공원2 = Location.create("판교 울세 공원", "37.3843077,127.117645");
		Location 시흥삼성전자서비스센터 = Location.create("시흥삼성전자서비스센터", "37.3411687,126.8155121");

		redisTemplate.opsForGeo().add("order", 판교_중앙_공원.toPoint(), 판교_중앙_공원.name);
		redisTemplate.opsForGeo().add("order", 판교_현대_백화점.toPoint(), 판교_현대_백화점.name);
		redisTemplate.opsForGeo().add("order", 판교_울세_공원.toPoint(), 판교_울세_공원.name);
		redisTemplate.opsForGeo().add("order", 판교_울세_공원2.toPoint(), 판교_울세_공원2.name);
		redisTemplate.opsForGeo().add("order", 시흥삼성전자서비스센터.toPoint(), 시흥삼성전자서비스센터.name);

		double dist = 2;
		Circle circle = new Circle(판교_울세_공원.toPoint(), new Distance(dist, Metrics.KILOMETERS));
		GeoResults<RedisGeoCommands.GeoLocation<String>> res = redisTemplate.opsForGeo().radius("order", circle);
		List<String> names = res.getContent().stream()
			.map(GeoResult::getContent)
			.peek(stringGeoLocation -> System.out.println(stringGeoLocation.getName()))
			.map(GeoLocation::getName)
			.toList();

		Assertions.assertThat(names).containsAll(List.of("판교 울세 공원", "판교 중앙 공원", "판교 현대 백화점"));
	}

}
