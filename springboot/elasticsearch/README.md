### 검색 성능 비교 Mysql, Elastic 

**테스트: python**


### mac 실행 가이드

- 최초 1번 진행
> brew install python

> $mkdir ~/${PACKAGE_NAME}\
> $cd ~/${PACKAGE_NAME}\
> $python3 -m venv venv\
> $pip install requests

- 매번 확인 [(venv) 마크가 커멘더라인 앞에 위치해야함] 
> source ~/${PACKAGE_NAME}/venv/bin/activate => 커멘더 라인에 맨 앞에 '(venv)' 마크가 표시된다.

- 실행 
> python stressTest.py

- 실행 후 화면 

```shell
-------------------        starting stress test... -------------------
MySQL average response time over 10000 requests: 0.1014 seconds
MySQL error rate: 0.00%
Elasticsearch average response time over 10000 requests: 0.0200 seconds
Elasticsearch error rate: 0.00%
-------------------   END  -------------------
```

### 결과
- 1만개 동시요청 시 ElasticSearch 가 5배나 더 빠르다.
- 요청 수가 증가하면 증가할 수록 ElasticSearch 기하급수적으로 검색 성능이 좋다.