FROM postgis/postgis:16-3.4

# ko_KR.UTF-8 로케일 생성
RUN apt-get update \
 && apt-get install -y locales \
 && sed -i '/ko_KR.UTF-8/s/^# //g' /etc/locale.gen \
 && locale-gen ko_KR.UTF-8 \
 && rm -rf /var/lib/apt/lists/*

ENV LANG=ko_KR.UTF-8 \
    LC_ALL=ko_KR.UTF-8

# entrypoint는 공식 기본값 유지 (중요)