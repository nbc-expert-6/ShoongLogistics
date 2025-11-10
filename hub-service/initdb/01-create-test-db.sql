-- 기본값
\set enc 'UTF8'
\set lc  'ko_KR.UTF-8'
\set tmpl 'template0'

-- helper: 존재하지 않으면 CREATE DATABASE 실행 (psql의 \gexec 이용)
-- 주의: hub_service 는 POSTGRES_DB로 이미 생성되므로 여기서는 만들지 않습니다.

\set db user_service
SELECT format(
               'CREATE DATABASE %I WITH ENCODING ''%s'' LC_COLLATE ''%s'' LC_CTYPE ''%s'' TEMPLATE %I',
               :'db', :'enc', :'lc', :'lc', :'tmpl'
       )
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = :'db')\gexec

    \set db company_service
SELECT format(
               'CREATE DATABASE %I WITH ENCODING ''%s'' LC_COLLATE ''%s'' LC_CTYPE ''%s'' TEMPLATE %I',
               :'db', :'enc', :'lc', :'lc', :'tmpl'
       )
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = :'db')\gexec

    \set db order_service
SELECT format(
               'CREATE DATABASE %I WITH ENCODING ''%s'' LC_COLLATE ''%s'' LC_CTYPE ''%s'' TEMPLATE %I',
               :'db', :'enc', :'lc', :'lc', :'tmpl'
       )
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = :'db')\gexec

    \set db notification_service
SELECT format(
               'CREATE DATABASE %I WITH ENCODING ''%s'' LC_COLLATE ''%s'' LC_CTYPE ''%s'' TEMPLATE %I',
               :'db', :'enc', :'lc', :'lc', :'tmpl'
       )
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = :'db')\gexec

    \set db hub_service_test
SELECT format(
               'CREATE DATABASE %I WITH ENCODING ''%s'' LC_COLLATE ''%s'' LC_CTYPE ''%s'' TEMPLATE %I',
               :'db', :'enc', :'lc', :'lc', :'tmpl'
       )
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = :'db')\gexec