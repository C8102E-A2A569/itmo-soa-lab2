# Тестовые запросы для проверки API

## Service 1 - Music Band Management API

### 1. Создание группы Queen
```bash
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Queen",
    "coordinates": {"x": 100, "y": 200},
    "numberOfParticipants": 4,
    "albumsCount": 15,
    "genre": "PROGRESSIVE_ROCK",
    "studio": {"name": "Abbey Road Studios", "address": "3 Abbey Road, London"}
  }'
```

### 2. Создание группы The Beatles
```bash
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{
    "name": "The Beatles",
    "coordinates": {"x": 50, "y": 150},
    "numberOfParticipants": 4,
    "albumsCount": 13,
    "genre": "PROGRESSIVE_ROCK",
    "studio": {"name": "Abbey Road Studios", "address": "3 Abbey Road, London"}
  }'
```

### 3. Создание группы Eminem (HIP_HOP)
```bash
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Eminem",
    "coordinates": {"x": 200, "y": 300},
    "numberOfParticipants": 1,
    "albumsCount": 11,
    "genre": "HIP_HOP",
    "studio": {"name": "Shady Records", "address": "Detroit, Michigan"}
  }'
```

### 4. Получение всех групп
```bash
curl -k https://localhost:8443/api/bands
```

### 5. Получение группы по ID
```bash
curl -k https://localhost:8443/api/bands/1
```

### 6. Обновление группы
```bash
curl -k -X PUT https://localhost:8443/api/bands/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Queen (Updated)",
    "coordinates": {"x": 100, "y": 200},
    "numberOfParticipants": 5,
    "albumsCount": 16,
    "genre": "PROGRESSIVE_ROCK",
    "studio": {"name": "Abbey Road Studios", "address": "3 Abbey Road, London"}
  }'
```

### 7. Удаление группы
```bash
curl -k -X DELETE https://localhost:8443/api/bands/1
```

### 8. Получение с фильтрацией по жанру
```bash
curl -k "https://localhost:8443/api/bands?filterBy=genre=PROGRESSIVE_ROCK"
```

### 9. Получение с сортировкой по имени
```bash
curl -k "https://localhost:8443/api/bands?sortBy=name"
```

### 10. Получение с сортировкой (DESC) по albumsCount
```bash
curl -k "https://localhost:8443/api/bands?sortBy=-albumsCount"
```

### 11. Пагинация (страница 0, размер 2)
```bash
curl -k "https://localhost:8443/api/bands?page=0&size=2"
```

### 12. Комбинированный запрос
```bash
curl -k "https://localhost:8443/api/bands?sortBy=-albumsCount&filterBy=genre=PROGRESSIVE_ROCK&page=0&size=10"
```

### 13. Среднее количество альбомов
```bash
curl -k https://localhost:8443/api/bands/albums/average
```

### 14. Группа с максимальной датой создания
```bash
curl -k https://localhost:8443/api/bands/max-creation-date
```

### 15. Поиск по подстроке в имени
```bash
curl -k "https://localhost:8443/api/bands/search/name-contains?substring=Queen"
```

---

## Service 2 - Grammy Awards API

### 16. Удаление участника из группы
```bash
curl -k -X DELETE https://localhost:8444/grammy/band/1/participants/remove
```

### 17. Награждение группы Grammy
```bash
curl -k -X POST https://localhost:8444/grammy/band/1/reward/PROGRESSIVE_ROCK
```

### 18. Попытка наградить в несоответствующем жанре (должна вернуть ошибку)
```bash
curl -k -X POST https://localhost:8444/grammy/band/1/reward/HIP_HOP
```

### 19. Награждение группы HIP_HOP
```bash
curl -k -X POST https://localhost:8444/grammy/band/3/reward/HIP_HOP
```

### 20. Попытка повторного награждения (должна вернуть ошибку)
```bash
curl -k -X POST https://localhost:8444/grammy/band/1/reward/PROGRESSIVE_ROCK
```

---

## Проверка связи между сервисами

### Сценарий 1: Удаление участника
1. Создать группу через Service 1
2. Проверить количество участников
3. Удалить участника через Service 2
4. Проверить, что количество уменьшилось в Service 1

```bash
# 1. Создать
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Band","coordinates":{"x":1,"y":2},"numberOfParticipants":5,"albumsCount":3,"genre":"HIP_HOP","studio":{"name":"Studio A"}}'

# 2. Проверить (предположим ID=1)
curl -k https://localhost:8443/api/bands/1

# 3. Удалить участника через Service 2
curl -k -X DELETE https://localhost:8444/grammy/band/1/participants/remove

# 4. Проверить снова
curl -k https://localhost:8443/api/bands/1
# numberOfParticipants должно быть 4
```

### Сценарий 2: Награждение
1. Создать группу через Service 1
2. Наградить через Service 2
3. Попытка наградить повторно должна вернуть ошибку

```bash
# 1. Создать
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{"name":"Award Test","coordinates":{"x":10,"y":20},"numberOfParticipants":3,"albumsCount":5,"genre":"PROGRESSIVE_ROCK","studio":{"name":"Studio B"}}'

# 2. Наградить (предположим ID=2)
curl -k -X POST https://localhost:8444/grammy/band/2/reward/PROGRESSIVE_ROCK

# 3. Повторная попытка (должна вернуть 422)
curl -k -X POST https://localhost:8444/grammy/band/2/reward/PROGRESSIVE_ROCK
```

---

## Тестирование ошибок

### 1. Создание с невалидными данными (y > 566)
```bash
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{"name":"Invalid","coordinates":{"x":1,"y":600},"numberOfParticipants":1,"albumsCount":1,"genre":"HIP_HOP","studio":{"name":"S"}}'
```

### 2. Создание с numberOfParticipants = 0
```bash
curl -k -X POST https://localhost:8443/api/bands \
  -H "Content-Type: application/json" \
  -d '{"name":"Invalid","coordinates":{"x":1,"y":2},"numberOfParticipants":0,"albumsCount":1,"genre":"HIP_HOP","studio":{"name":"S"}}'
```

### 3. Получение несуществующей группы
```bash
curl -k https://localhost:8443/api/bands/999999
```

### 4. Удаление участника из несуществующей группы
```bash
curl -k -X DELETE https://localhost:8444/grammy/band/999999/participants/remove
```

### 5. Награждение несуществующей группы
```bash
curl -k -X POST https://localhost:8444/grammy/band/999999/reward/HIP_HOP
```

---

## Проверка Swagger UI

- Service 1: https://localhost:8443/swagger-ui.html
- Service 2: https://localhost:8444/swagger-ui.html

В браузере можно тестировать все эндпоинты интерактивно.