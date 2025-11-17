# 📣 EVENT_GUIDE.md

---

## ✅ 1. 이벤트 네이밍 규칙

- 🔹 **규칙:** 과거형 사용 `(OrderCreatedEvent, PaymentCompletedEvent, UserRegisteredEvent)`
- 🔹 **형식:** 도메인명 + 과거분사 + Event
- 🔹 **팁:**
    - 이벤트 이름만 보고 어떤 도메인에서 어떤 동작이 발생했는지 알 수 있어야 함
    - 팀 내 통일된 네이밍으로 추후 이벤트 추적 용이

---

## ✅ 2. 리스너 작성 가이드

- 🔹 **사용 어노테이션:** `@TransactionalEventListener`
- 🔹 **핵심 포인트:** 트랜잭션 완료 시점에 이벤트를 안전하게 처리
- 🔹 **phase 옵션**

| 옵션             | 호출 시점      | 용도               |
|----------------|------------|------------------|
| BEFORE_COMMIT  | 트랜잭션 커밋 직전 | 커밋 전 검증, 사전 처리   |
| AFTER_COMMIT   | 트랜잭션 커밋 후  | 외부 알림, 통계, 메시징   |
| AFTER_ROLLBACK | 트랜잭션 롤백 후  | 실패 알림, 보상 처리, 로깅 |

- 🔹 **비동기 처리 (`@Async`)**
    - 동기 처리 시 UX 저해, 메인 트랜잭션 부담 시 사용
    - 별도 스레드에서 실행 → 테스트 시 로그로 확인
    - 비동기 이벤트 예외 발생 시 메인 로직 영향 없음

- 🔹 **예외 처리**
    - 리스너 내 예외는 반드시 처리 (`try-catch`)
    - 필요 시 재처리 큐 또는 `Dead Letter Queue(DLQ)` 사용
    - 로그 기록 필수 🔥

---

## ✅ 3. 테스트 작성 가이드

- 🔹 **검증 항목**
    - 이벤트 발행 여부 확인
    - 비동기 처리 여부 확인 (다른 스레드에서 실행)
    - 트랜잭션 commit/rollback 시점 이벤트 발행 여부 `verify` 검증

---

# 💬 필수 테스트 체크리스트

- [ ] 단일 트랜잭션 내 이벤트 발행 확인
- [ ] 실패 케이스 이벤트 발행 안됨 검증
- [ ] 비동기 이벤트가 별도 스레드에서 실행되는지 확인
- [ ] 이벤트 발행 → 리스너 처리 → 상태 변경 체이닝 확인
- [ ] 트랜잭션 commit/rollback 시 이벤트 발행 여부 검증

---

### 1. 이벤트 발행 테스트

- 단일 트랜잭션 내에서 이벤트 발행 여부를 확인
- 이벤트 리스너가 실제 호출되는지 ```verify``` 사용
- 실패 케이스도 포함 : 예외 발생 시 이벤트가 발행되지 않아야함

```java

@DisplayName("주문 생성 시 OrderCreatedEvent가 발행 된다.")
@Test
void createOrder_ShouldPublishOrderCreatedEvent() {
	// given
	//주문 객체 생성

	// when
	orderService.createOrder(command);

	// then
	verify(orderEventListener, times(1))
		.handleOrderCreated(any(OrderCreatedEvent.class));
}

@DisplayName("주문 생성 실패 시 OrderCreatedEvent가 발행되지 않는다.")
@Test
void createOrderFail_ShouldNotPublishOrderCreatedEvent() {
	// given
	//주문 객체 생성

	// when & then
	assertThrows(RuntimeException.class, () -> orderService.createOrder(command));
	verify(orderEventListener, times(0))
		.handleOrderCreated(any(OrderCreatedEvent.class));
}
```

### 2. Async가 적용 된 이벤트 발행 테스트

- 이벤트 리스너는 별도 thread에서 실행
- 리스너에서 예외가 발생하더라도 메인 트랜잭션이나 호출 로직은 실패하지 않아야 함
- ```verify```로 비동기 호출 여부 확인

```java

@Test
@DisplayName("비동기 처리 중 예외가 발생해도 메인 로직에 영향을 주지 않는다")
void whenAsyncEventThrowsException_MainFlowShouldNotBeAffected() {
	// given
	CreateOrderCommand command = createCommand();

	// 이벤트 처리중 예외 강제 발생
	doAnswer(invocation -> {
		throw new RuntimeException("주문 생성 이벤트 처리 실패");
	}).when(orderEventListener)
		.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));

	// when & then
	// 비동기 작업에서 예외가 발생해도, 메인 로직(주문 생성)은 성공
	assertThatCode(() -> orderService.createOrder(command))
		.doesNotThrowAnyException();
	// 비동기 이벤트가 호출됐는지 검증
	verify(orderEventListener, times(1))
		.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));
}
```

### 3. 실제 flow 체이닝 테스트

- 이벤트 발행 -> 리스너 처리 -> 상태 변경 순서 검증
- 트랜잭션, 비동기 처리, 상태 변화까지 전체 흐름 테스트
- 필요 시 ```Thread.sleep()``` 또는 ```Awaitility``` 사용해 비동기 완료 대기

```java

@Test
@DisplayName("주문 생성부터 재고 차감까지 전체 플로우가 정상 동작한다")
void fullOrderFlow_ShouldWorkCorrectly() throws InterruptedException {
	// given
	// 주문 요청 생성
	CreateOrderCommand command = createSuccessfulCommand();
	Stock stock = Stock.create(
		command.orderItems().get(0).productId(),
		Quantity.of(100)
	);
	stockRepository.save(stock);

	// when
	//주문생성 실행
	UUID orderId = orderService.createOrder(command);

	// 비동기 처리를 위한 대기
	Thread.sleep(3000);

	// then
	// 주문 생성 확인
	// 주문은 결제 완료후 이벤트 처리해서 PAID -> SHIPPED로 바뀌어야함
	Order order = orderRepository.findById(orderId).orElseThrow();
	assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);

	// 결제 생성 확인
	Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow();
	assertThat(payment.getOrderId()).isEqualTo(orderId);
	assertThat(payment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);

	// 재고 차감 확인
	Stock updatedStock = stockRepository.findByProductId(stock.getProductId()).orElseThrow();
	assertThat(updatedStock.getQuantity().getQuantity()).isEqualTo(90);
}
```