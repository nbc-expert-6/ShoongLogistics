FROM postgis/postgis:16-3.4

# 타임존 및 로케일 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    apt-get update && \
    apt-get install -y locales && \
    sed -i 's/# ko_KR.UTF-8 UTF-8/ko_KR.UTF-8 UTF-8/' /etc/locale.gen && \
    locale-gen && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 환경 변수 설정
ENV LANG=ko_KR.utf8 \
    LC_COLLATE=ko_KR.utf8 \
    POSTGRES_INITDB_ARGS=--data-checksums

# PostGIS 자동 활성화 스크립트 (선택사항)
COPY ./init-postgis.sh /docker-entrypoint-initdb.d/