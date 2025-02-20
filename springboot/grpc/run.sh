#!/bin/bash

JAR_PATH=./build/libs/grpc-0.0.1-SNAPSHOT.jar

echo "build 슝슝슝슝슝슝 \n"
./gradlew clean build

if [ $? -ne 0 ]; then
  echo "❌ 빌드 실패! 스크립트를 종료합니다."
  exit 1
fi
echo "🌸 빌드 끗 .!\n\n\n"

echo "🚀 gRPC 서버를 시작 \n"
java -jar $JAR_PATH --spring.profiles.active=grpc-server&SERVER_PID=$!

sleep 3

echo "🚀 gRPC 클라이언트를 시작 \n"
java -jar $JAR_PATH --spring.profiles.active=grpc-client&CLIENT_PID=$!

echo "✅ gRPC 서버 PID: $SERVER_PID"
echo "✅ gRPC 클라이언트 PID: $CLIENT_PID"

# Ctrl+C 입력 시 서버/클라이언트 종료
trap "echo '🛑 종료 중...'; kill $SERVER_PID $CLIENT_PID; exit 0" SIGINT SIGTERM

# 무한 대기 (Ctrl+C로 종료 가능)
wait