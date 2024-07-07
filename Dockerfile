FROM openjdk:17-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR файл приложения в контейнер
COPY target/MilanaRestoran-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, на котором будет работать приложение
EXPOSE 4040

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "app.jar"]
