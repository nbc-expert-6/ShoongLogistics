FROM eclipse-temurin:17-jdk

WORKDIR /app

# 앱과 초기화 파일 복사
COPY build/libs/hub-service-*.jar app.jar
COPY hubinit.http /app/hubinit.http

# httpie 설치
RUN apt-get update && apt-get install -y httpie netcat && rm -rf /var/lib/apt/lists/*

# 엔트리포인트 스크립트 복사
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 19092

# 앱과 초기화 실행을 entrypoint.sh에서 처리
ENTRYPOINT ["/app/entrypoint.sh"]