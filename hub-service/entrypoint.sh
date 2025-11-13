#!/bin/bash

# 1. 앱 실행 (백그라운드)
java -jar app.jar &

# 2. 앱이 켜질 때까지 대기
echo "Waiting for Hub service to start..."
while ! nc -z localhost 19092; do
  sleep 1
done

echo "Hub service is up. Running initial HTTP requests..."

# 3. hubinit.http 실행
http -f < hubinit.http

# 4. 메인 프로세스 유지
wait