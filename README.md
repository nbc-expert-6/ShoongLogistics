# 🚚📦 ShoongLogistics B2B MSA Project

프로젝트 간단 요약 및 소개

- **프로젝트 목적**: ShoongLogistics는 단순한 주문.배달 서비스에서 나아가 기업 간 물류 관리와 배송 프로세스를 운영할 수 있는 MSA 기반 플랫폼을 목표로 개발되었습니다.
- **주요 기능**: 회원 인증.인가(JWT 기반), 주문 및 배달 관리, 허브 및 업체 관리
- **개발 기간**: 2025/10/31 ~ 2025/11/13 ( 2 weeks )

---

## 📌 프로젝트 목적 / 상세

### 1. 회원 서비스

- 역할(Role) 기반 인증/인가(MASTER, HUB_MANAGER, COMPANY_MANAGER, SHIPPER 등)
- JWT 토큰 발급 및 유효성 검증
- Spring Security 기반 세션 무관 인증 구조

### 2. 허브 서비스

- 중앙 허브(CENTRAL) 및 일반 허브(NORMAL) 관리
- 허브별 위치(위도/경도) 기반 관리
- 허브 간 이동 경로 간 최적화

### 3. 주문 및 배달 서비스

- 주문 생성 및 상태 관리
- 배송 생성 및 상태 관리
- 배송담당자 할당
- 허브 간 물류 이동 기록 관리

### 4. 업체 및 상품 서비스

- 업체 등록(MANUFACTURER, RECEIVER 구분)
- 허브 기반 업체 소속 관리
- 상품 등록/수정/삭제 관리
- 재고 관리

### 5. 알림 서비스

- Slack Webhook 기반 실시간 알림
- 주문 생성 시 알림 전송
- AI 기반 응답 메세지 작성

---

## 👥 팀원 역할 분담

| 이름   | 역할   | 담당 내용                          |
|------|------|--------------------------------|
| 강태성  | 팀장   | 주문 및 배달 서비스 담당, 배포환경 구성, 팀문서화  |
| 이나라힘 | 테크리더 | 업체 및 상품 서비스 담당, 부하 테스트, 인프라 설정 |
| 김다애  | 팀원   | 허브 서비스 담당, 발표자료 작성             |
| 이수현  | 팀원   | 회원 서비스 담당, 모니터링 및 메트릭 인프라 설정   |

---

## ⚙ 서비스 구성 및 실행 방법

### 설계 철학

- **DDD(Domain-Driven Design)** 기반 서비스 설계: 도메인 중심으로 계층과 책임을 명확히 구분
- **클린 아키텍처(Clean Architecture)** 적용: 의존성 규칙을 통해 Presentation, Application, Domain, Infrastructure 계층 분리  
  → 유지보수 용이, 테스트 용이성 확보

### 환경 설정 (Prerequisites)

- Java 17 이상
- Spring Boot 3.5.7 이상
- PostgreSQL 16 이상
- Gradle 8.14.3 이상

### 실행방법

> __1. 프로젝트 클론__
<br>

> __2. 프로젝트.zip파일 압축 해제__
<br>

> __3. notification-service 루트경로에 있는 .env를 클론받은 notification-service 루트경로로 복사__
<br>

> __4. 실행중인 컨테이너들 종료 후 루트 프로젝트에 위치한 docker-compose 실행__
<br>

> __5. hub-service 루트경로에 위치한 .http파일 실행하여 초기 허브 데이터 삽입__
<br>

> __6. hub-service 컨테이너 재실행하여 허브 간 운송경로 데이터 생성__

---

## 🗂 ERD

<img width="2973" height="1615" alt="Image" src="https://github.com/user-attachments/assets/8722a581-f1b9-4289-8c68-f05b38fb5f04" />

---

## 시스템 아키텍쳐

<img width="2475" height="1307" alt="image" src="https://github.com/user-attachments/assets/b2b6c9e6-91ee-47f3-a234-f16d382c6ac5" />

---

## 📄 API 문서

- **Swagger UI (로컬 실행 기준)**  
  백엔드 서버를 실행한 후 아래 URL에서 확인 가능:  
  [http://localhost:8000/swagger-ui/index.html](http://localhost:8000/swagger-ui/index.html)

- **API 주요 기능**
    - 회원 서비스: 가입, 로그인, 정보 조회/수정/삭제
    - 허브 서비스: 허브 생성/조회/수정
    - 주문/배달 서비스: 주문 생성, 배달 상태 관리
    - 업체/상품 서비스: 등록/수정/삭제, 재고 관리
    - 알림 서비스: Slack 알림 전송

---

## 🔧 기술 스택

### Back-end

- **Java**
- **Spring Boot**
    - Spring Security
    - Spring AI
    - JWT
    - JUnit
    - Lombok
    - Actuator
- **JPA (Hibernate)**

### Database

- PostgreSQL(with PostGIS)

### Infrastructure

- Docker
- Grafana
- nGrinder
- Redis
- Prometheus

### CI/CD

- Github Actions를 이용해 PR 시 자동 빌드 및 테스트 실행
- dev 브랜치 병합 전 코드 품질을 검증하도록 설정

### Tools

- Github
- Figma
- Erd Cloud
- Postman
- Swagger
- Slack
- Notion

