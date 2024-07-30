#!/bin/bash

# 애플리케이션 디렉토리로 이동
cd /home/api/api_back

# 기존 애플리케이션 종료
if pgrep -f "your-application-name"; then
  pkill -f "your-application-name"
fi

# 새로운 애플리케이션 시작
nohup java -jar your-application.jar > /dev/null 2>&1 &

# Nginx 재시작
systemctl restart nginx