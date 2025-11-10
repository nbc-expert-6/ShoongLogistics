# TEST_GUIDE.md

## 🧪 테스트 작성 가이드

이 문서는 프로젝트 내 테스트 코드 작성 시 **일관성**과 **품질**을 유지하기 위한 표준 가이드라인입니다.  
모든 팀원은 아래 규칙을 기반으로 테스트를 작성해야 하며, 코드리뷰 시 이 기준을 준수했는지 확인합니다.

---

## ⚙️ 테스트 환경 설정 (필수)

테스트 실행 전, 로컬 환경에서 **PostgreSQL 컨테이너**를 실행해야 합니다.  
아래 명령어를 통해 테스트용 DB 환경을 구성합니다.

### 1️⃣ Docker 컨테이너 실행

  ```bash
  # 루트 디렉토리에서 실행
  docker-compose up -d
  version: '3.8'
  
  services:
    postgres:
      build:
        context: ./.github/actions/postgres-setup
        dockerfile: Dockerfile
      container_name: shoong-postgis
      environment:
        POSTGRES_DB: postgres
        POSTGRES_USER: postgres
        POSTGRES_PASSWORD: qwer1234!
      ports:
        - "5432:5432"
      healthcheck:
        test: ["CMD-SHELL", "pg_isready -U postgres"]
        interval: 10s
        timeout: 5s
        retries: 5
      networks:
        - shoong-network
  
  networks:
    shoong-network:
      driver: bridge


## ✅ 테스트 구성 요소

### 1. Domain Entity 테스트

- **목적:**
- 도메인 객체(Entity)의 **비즈니스 규칙**, **불변성**, **제약 조건**을 검증하기 위함
- Entity 내부의 로직(`validate`, `change`, `calculate` 등)이 명세대로 동작하는지 확인
    - **예시:**
        - `User` 엔티티의 회원생성 로직이 아이디, 비밀번호 정책을 지키는지 테스트
      ```java
        @Test
        @DisplayName("아이디 길이가 4자 미만이면 예외 발생")
        void createUser_shortUserName_throwsException() {
            // given
            String userName = "abc";
            String password = "Brian981103!";
  
            // when & then
            assertThatThrownBy(() -> User.create(userName, password, UserRole.MASTER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 4자 이상 10자 이하이어야 합니다.");
        }

---

### 2. Domain Service 테스트

- **목적:** 순수한 비즈니스 로직을 포함하는 서비스의 동작 검증
- **특징:** 외부 의존성이 없는 순수 로직이므로 **Mock 객체를 최소화**하고, 실제 도메인 객체로 테스트할 것
- **예시:**
    - `ORDER-SERVICE`의 총 주문 금액 검증 및 주문 항목 수 검증 테스트

---

### 3. Infrastructure Repository 테스트

- **목적:** DB 연동, JPA 쿼리 동작, 트랜잭션 전파 등의 실제 영속성 테스트
    - **예시:**
        - `UserRepository.findUsersByUserName(String userName)` 쿼리가 올바르게 동작하는지 검증
        - `@Query` 기반 JPQL 실행 결과 확인
      ```java
        @Autowired
        private CompanyRepositoryAdapter companyRepositoryAdapter;

        @Test
        @DisplayName("업체를 저장할 수 있다")
        void save() {
        // Given
        Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
        
                // When
                Company savedCompany = companyRepositoryAdapter.save(company);
        
                // Then
                assertThat(savedCompany.getId()).isNotNull();
                assertThat(savedCompany.getName()).isEqualTo("서울 제조 업체");
                assertThat(savedCompany.getType()).isEqualTo(CompanyType.MANUFACTURER);
        }

---

## 🧩 테스트 네이밍 규칙

- **형식:** {메소드명}_{상태}_{결과}
- **의도:** 테스트의 목적과 기대 결과를 명확하게 표현하여 가독성을 높임
- **예시:**
- `registerUser_중복예외_예외발생()`
- `changeStatus_유효하지않은상태전환_예외발생()`

---

## 🧱 Mock 사용 가이드

### 1. Mock 사용 원칙

- **도메인 계층(domain)**에서는 **Mock 사용 금지**  
  → 순수 비즈니스 로직 검증이 목적이므로 실제 객체로 테스트해야 함
- **애플리케이션 계층(application)**에서는 **Mock 활용**  
  → 외부 의존성(Repository, 외부 API, 메시지 브로커 등)을 대체하여 단위 테스트를 빠르게 수행
- **인프라 계층(infra)**에서는 **실제 DB나 외부 시스템과의 통합 테스트를 우선**

---

### 2. Mockito 사용 예시

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void registerUser_중복이메일_예외발생() {
      // given
      given(userRepository.existsByEmail(anyString())).willReturn(true);

      // when & then
      assertThrows(DuplicateUserException.class, 
          () -> userService.registerUser("test@example.com", "password"));
  }
}
