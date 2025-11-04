#!/bin/bash
set -e

echo "=== Starting PostGIS setup ==="

# 시스템 locale 확인 (디버깅용)
echo "Current locale settings:"
locale

# MSA 서비스별 데이터베이스 생성
DATABASES=(
    "user_service"
    "company_service"
    "order_service"
    "hub_service"
    "notification_service"
)

for db in "${DATABASES[@]}"
do
    echo "Creating database: $db"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
        SELECT 'CREATE DATABASE $db'
        WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$db')\gexec
EOSQL

    echo "Installing PostGIS on: $db"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
        CREATE EXTENSION IF NOT EXISTS postgis;
        CREATE EXTENSION IF NOT EXISTS postgis_topology;
EOSQL
done

# 생성된 DB 목록 확인(디버깅용)
echo "=== Database list ==="
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" -c "\l"
echo "Setup completed!"