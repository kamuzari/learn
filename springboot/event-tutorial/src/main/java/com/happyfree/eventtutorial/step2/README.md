### DDD event flow by Logging

1. 트랜잭션 획득 
2. -> 비즈니스 처리 
3. -> 이벤트 발행 
4. -> 트랜잭션 커밋 ->
5. 이벤트 수신 ->
6. 트랜잭션 시작 ->
7. 비즈니스 처리 ->
8. 트랜잭션 종료  
```shell

TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [com.happyfree.eventtutorial.step2.service.OrderService.cancelOrder]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
 WARN 22083 --- [event-tutorial] [  restartedMain] c.h.e.s.event.producer.EventsProducer    : raise event: com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent@5dd8af91
DEBUG 22083 --- [event-tutorial] [  restartedMain] actionalApplicationListenerMethodAdapter : Registered transaction synchronization for org.springframework.context.PayloadApplicationEvent[source=org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@b008311, started on Sun Dec 22 22:55:33 KST 2024]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.happyfree.eventtutorial.step2.service.OrderService.cancelOrder]

 INFO 22083 --- [event-tutorial] [  restartedMain] c.h.e.s.e.h.OrderCancelEventHandler      : ======== expected previous transaction commited ======
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [com.happyfree.eventtutorial.step2.service.ExteriorService.refund]
ERROR 22083 --- [event-tutorial] [  restartedMain] c.h.e.step2.service.ExteriorService      : Refund Order Id: c8f4d1ec-5a5a-49a9-acdf-fa4cdca0e1a4
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
ERROR 22083 --- [event-tutorial] [  restartedMain] c.h.e.step2.service.ExteriorService      : order cancel history: OrderCancelHistory[id='aa3811ee-d2e4-48f0-9b20-ecf8b26b8d80', order=Order[id='c8f4d1ec-5a5a-49a9-acdf-fa4cdca0e1a4', shipStatus=CANCELED], createdAt=2024-12-22T22:55:35.362076]
TRACE 22083 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.happyfree.eventtutorial.step2.service.ExteriorService.refund]
TRACE 22083 --- [event-tutorial] [  restartedMain] actionalApplicationListenerMethodAdapter : No result object given - no result to handle

```


### 최종 로그 with SQL query
```shell

 :40:12.438+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [com.happyfree.eventtutorial.step2.service.OrderService.createOrder]
 :40:12.439+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
 :40:12.447+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    select
        o1_0.id,
        o1_0.ship_status 
    from
        orders o1_0 
    where
        o1_0.id=?
 :40:12.466+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
 :40:12.466+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.happyfree.eventtutorial.step2.service.OrderService.createOrder]
 :40:12.470+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    insert 
    into
        orders
        (ship_status, id) 
    values
        (?, ?)
 :40:14.478+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [com.happyfree.eventtutorial.step2.service.OrderService.cancelOrder]
 :40:14.480+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
 :40:14.484+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    select
        o1_0.id,
        o1_0.ship_status 
    from
        orders o1_0 
    where
        o1_0.id=?
 :40:14.488+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
 :40:14.488+09:00  WARN 23163 --- [event-tutorial] [  restartedMain] c.h.e.s.event.producer.EventsProducer    : raise event: com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent@5375bd3f
 :40:14.491+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] actionalApplicationListenerMethodAdapter : Registered transaction synchronization for org.springframework.context.PayloadApplicationEvent[source=org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@7df6dd0a, started on Sun Dec 22 23:40:11 KST 2024]
 :40:14.491+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.happyfree.eventtutorial.step2.service.OrderService.cancelOrder]
 :40:14.496+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    update
        orders 
    set
        ship_status=? 
    where
        id=?
 :40:14.500+09:00  INFO 23163 --- [event-tutorial] [  restartedMain] c.h.e.s.e.h.OrderCancelEventHandler      : ======== expected previous transaction commited ======
 :40:14.501+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [com.happyfree.eventtutorial.step2.service.ExteriorService.refund]
 :40:14.501+09:00 ERROR 23163 --- [event-tutorial] [  restartedMain] c.h.e.step2.service.ExteriorService      : Refund Order Id: bbece180-25df-40fb-afdb-e2322db37136
 :40:14.501+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
 :40:14.501+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    select
        o1_0.id,
        o1_0.ship_status 
    from
        orders o1_0 
    where
        o1_0.id=?
 :40:14.503+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]
 :40:14.503+09:00  INFO 23163 --- [event-tutorial] [  restartedMain] c.h.e.step2.service.ExteriorService      : 주문 취소 이벤트 발행한 order --> Order[id='bbece180-25df-40fb-afdb-e2322db37136', shipStatus=CANCELED]
 :40:14.504+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
 :40:14.505+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    select
        och1_0.id,
        och1_0.created_at,
        och1_0.order_id 
    from
        order_cancel_history och1_0 
    where
        och1_0.id=?
 :40:14.505+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
 :40:14.505+09:00 ERROR 23163 --- [event-tutorial] [  restartedMain] c.h.e.step2.service.ExteriorService      : order cancel history: OrderCancelHistory[id='591b32e6-114d-439c-8083-242800478899', order=bbece180-25df-40fb-afdb-e2322db37136, createdAt= :40:14.504112]
 :40:14.506+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.happyfree.eventtutorial.step2.service.ExteriorService.refund]
 :40:14.506+09:00 DEBUG 23163 --- [event-tutorial] [  restartedMain] org.hibernate.SQL                        : 
    insert 
    into
        order_cancel_history
        (created_at, order_id, id) 
    values
        (?, ?, ?)
 :40:14.507+09:00 TRACE 23163 --- [event-tutorial] [  restartedMain] actionalApplicationListenerMethodAdapter : No result object given - no result to handle
```