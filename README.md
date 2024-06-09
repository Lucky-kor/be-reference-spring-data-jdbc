# Spring Data JDBC 레퍼런스 코드

### Description
Spring Data JDBC 레퍼런스 코드는 학습 컨텐츠에서 사용되는 예제 코드 + 라이브 세션에서 사용되는 예제 코드로 구성이 되어 있습니다.
* 학습 컨텐츠용 예제 코드
  * [Hello World 예제 코드](#hello-world-예제-코드)
  * Spring Data JDBC를 통한 데이터 액세스 계층 구현 예제 코드
* 라이브 세션용 예제 코드
  * [레거시 데이터 액세스 기술 예제 코드](#레거시-데이터-액세스-기술-예제-코드)
  * [N + 1 문제에 대한 예제 코드](#n--1-문제에-대한-예제-코드)
  * [DTO 클래스와 엔티티 클래스에서 타입을 맞추어 주는 변환 방식에 대한 예제 코드](#DTO-클래스와-엔티티-클래스에서-타입을-맞추어-추는-변환-방식에-대한-예제-코드)
  * [Value Object를 타입으로 사용하기 위한 Converter 사용 예제 코드](#value-object를-타입으로-사용하기-위한-converter-사용-예제-코드)
  
> 예제 코드에 대한 더 구체적인 정보는 아래에서 확인하세요.

---

### Hello World 예제 코드
Spring Data JDBC의 전체적인 동작 방식과 사용법을 이해하기 위한 Hello World 예제 코드이며, Spring Data JDBC를 이용하여 "Hello World" 메시지를 데이터베이스에 저장합니다.

* 소스 코드 경로
  * [src/main/java/com/springboot/hello_world](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/hello_world)

---

### 레거시 데이터 액세스 기술 예제 코드
SQL 쿼리 중심 기술에 대한 예제 코드이며, JDBC API와 Spring JDBC로 구성되어 있습니다.
* 소스 코드 경로
  * JDBC API 예제 코드
    * [src/main/java/com/springboot/legacy_example/jdbc_example](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/legacy_example/jdbc_example)
  * Spring JDBC 예제 코드
    * [src/main/java/com/springboot/legacy_example/spring_jdbc_example](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/legacy_example/spring_jdbc_example)

---

### N + 1 문제에 대한 예제 코드
N + 1 문제를 해결하기 위한 예제 코드입니다.
* **네이티브 쿼리를 이용한 N + 1 문제 해결 예제 코드**
  * Controller
    * [src/main/java/com/springboot/order/controller/OrderController.java](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/controller/OrderController.java)
    * `getOrders1()`
      * Step 1: 네이티브 쿼리로 Join된 주문한 커피 정보
    * `getOrders2()`
      * Step 2: 주문한 커피별로 그룹핑하기
    * `getOrders3()`
      * Step 3: 그룹핑된 주문한 커피 정보를 우리가 원하는 데이터 형식으로 변환하기
    * `getOrders4()`
      * Step 4: 최근 주문 순으로 정렬하기
    * `getOrders5()`
      * Step 5: OrderMapper를 이용해 코드 리팩토링
  * Mapper
    * [src/main/java/com/springboot/order/mapper/OrderMapper](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/mapper/OrderMapper.java)
    * `default List<OrderResponseDto> readableOrderCoffeeToOrderResponseDto(List<ReadableOrderCoffee> orders)`
      * 읽기 전용 엔티티인 `ReadableOrderCoffee`를 이용해 모든 주문에 대한 `OrderResponseDto` 매핑
    * `default List<OrderCoffeeResponseDto> readableOrderCoffeeToOrderCoffeeResponseDto(List<ReadableOrderCoffee> readableOrderCoffees)`
      * 읽기 전용 엔티티인 `ReadableOrderCoffee`를 이용해 한 겅의 주문에 대한 `OrderCoffeeResponseDto(주문한 커피 정보)` 매핑
  * Entity
    * [src/main/java/com/springboot/order/entity/ReadableOrderCoffee](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/entity/ReadableOrderCoffee.java)
    * 네이티브 쿼리를 이용해 주문한 커피 정보를 한 번에 가져오기 위한 읽기 전용 엔티티 클래스
  * Service
    * [src/main/java/com/springboot/order/service/OrderService](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/service/OrderService.java)
    * `findOrders2()`
      * ReadableOrderCoffee를 조회하는 서비스 메서드
  * Repository
    * [src/main/java/com/springboot/order/repository/OrderRepository](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/repository/OrderRepository.java)
    * `List<ReadableOrderCoffee> findAllOrderCoffee()`
      * 네이티브 쿼리의 JOIN 문을 이용해 주문한 커피 정보를 조회하는 쿼리 메서드
        
* **쿼리 메서드를 이용한 N + 1 문제 해결 예제 코드**
  * Mapper
    * [src/main/java/com/springboot/order/mapper/OrderMapper](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/mapper/OrderMapper.java)
    * `default List<OrderCoffeeResponseDto> orderToOrderCoffeeResponseDtoV2(CoffeeService coffeeService,
      Set<CoffeeRef> orderCoffees)`
      * N + 1 이슈가 없는 개선된 orderToOrderCoffeeResponseDto 버전
  * Service
    * [src/main/java/com/springboot/order/service/CoffeeService](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/coffee/service/CoffeeService.java)
    * `public List<Coffee> findAllCoffeesByIds(List<Long> coffeeIds)`
      * 주문한 커피 정보를 한 번에 조회한다

---

### DTO 클래스와 엔티티 클래스에서 타입을 맞추어 추는 변환 방식에 대한 예제 코드
Mapper의 코드는 단순해지지만 DTO 클래스와 Entity 클래스의 복잡도가 높아지고 책임 영역이 모호해지는 경향이 있습니다. 유지 보수를 용이하게 하기 위해서는 Mapper에서 더 많은 일을 하는 것이 낫습니다.
* DTO -> Entity로 변환하기 위해 Entity에서의 처리 방식 예제 코드
  * Mapper
    * [src/main/java/com/springboot/order/mapper/OrderMapper](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/mapper/OrderMapper.java)
    * `default Order2 orderPostDtoToOrder(OrderPostDto orderPostDto)`
      * 데이터 타입이 다른 필드에 대한 매핑 작업은 Order 엔티티 클래스에서 이루어진다.
  * Entity
    * [src/main/java/com/springboot/order/entity/Order2](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/entity/Order2.java)
    * `public void setMemberId(long memberId)`
      * `long memberId` -> `AggregateReference<Long> memberId`로 변환한다.
    * `public void setOrderCoffees(List<CoffeeRef> orderCoffees)`
      * `List<CoffeeRef> orderCoffees` -> `Set<CoffeeRef> convertedOrderCoffees`로 변환한다.
* Entity -> DTO로 변환하기 위해 DTO에서의 처리 방식 예제 코드
  * Mapper
    * [src/main/java/com/springboot/order/mapper/OrderMapper](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/fc692f0ca5c69a1c3c7a329d9036d353147afeb2/src/main/java/com/codestates/order/mapper/OrderMapper.java)
    * `default OrderResponseDto2 orderToOrderResponseDto(Order order)`
      * 데이터 타입이 다른 필드에 대한 매핑 작업은 OrderResponseDto2 DTO 클래스에서 이루어진다.
  * DTO
    * [src/main/java/com/springboot/order/dto/OrderResponseDto2](https://github.com/codestates-seb/be-reference-spring-data-jdbc/blob/8babfcc9e78a9c8594f88ab477988aa01f15fe5d/src/main/java/com/codestates/order/dto/OrderResponseDto2.java)
    * `public void setMemberId(AggregateReference<Member, Long> memberId)`
      * `AggregateReference<Member, Long> memberId` -> `long memberId`로 변환한다.
    * `public void setOrderCoffees(Set<CoffeeRef> coffeeRefs)`
      * `Set<CoffeeRef> coffeeRefs` -> `List<CoffeeRef> orderCoffees`로 변환한다.

---

### Value Object를 타입으로 사용하기 위한 Converter 사용 예제 코드
Spring Data JDBC에서 Value Object를 데이터 타입으로 사용하기 위해 Converter를 등록할 수 있습니다.
* Money 타입 Value Object
  * [src/main/java/com/springboot/values](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/values)
* JDBC Configuration
  * [src/main/java/com/springboot/config](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/config)
* Converter
  * [src/main/java/com/springboot/converter](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/converter)
* Mapper
  * [src/main/java/com/springboot/coffee/mapper](https://github.com/codestates-seb/be-reference-spring-data-jdbc/tree/main/src/main/java/com/codestates/coffee/mapper)
