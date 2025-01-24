## RabbitMQ

### prefetch count 동작원리 살펴보기

- 하나의 서버가 처리할 수 있는 양을 설정하는 부분이다.
  - prefetch count가 3이면 어떻게 동작할까?
  - 내가 예상하는 흐름
    1. 3개는 제각각 쓰레드에서 처리된다. ,이로써 3개를 병렬로 처리한다
       - 그리고, 이 3개가 모두 끝나야 다음 메시지 3개를 소비한다.
    2. 하나의 쓰레드에 의해 3개가 순차적으로 처리되는지 궁금하다
       - 병렬 처리는 되지 않는다.
       - 이랬을때, 2개는 빠르게 처리되었지만, 나머지 하나가 처리되지 않아 모두 끝나고 다음 메시지 3개를 받는다.
    
    - 이렇게 3개를 받고 모두 끝날때까지, 기다릴떄, 나머지 1개가 늦게 처리되어 다음 메시지 소비가 지연된다면 처리량이 좋지 못하다.
    - 과연 어떻게 동작할 지 궁금하다.
  - 