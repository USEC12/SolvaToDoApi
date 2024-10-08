# SolvaToDoApi
# To-Do List RESTful API

## Описание проекта

Это простое RESTful API для управления задачами To-Do List. Оно позволяет создавать задачи, обновлять их, удалять, получать список задач, а также фильтровать их по статусу. Проект также включает валидацию данных и проверку дат завершения задачи на выходные дни с использованием внешнего API.

## Технологический стек

- **Java 21+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **PostgreSQL** 
- **Maven**
- **JUnit 5** для тестирования
- **WebClient** для работы с внешними API
- **Swagger/OpenAPI** для документации
## Примеры эндпоинтов что бы было проще проверить в ручную
------------------
Создание задачи
 /tasks
 POST
Тело запроса (JSON):
{
  "title": "New Task",
  "description": "Description of the task",
  "dueDate": "2024-09-10"
}
-------------------
Получение списка задач
 /tasks
 GET
Параметры (опционально):
status (например, "выполнено" или "не выполнено")
-------------------
Обновление задачи

/tasks/{id}
 PUT
Тело запроса (JSON):
{
  "title": "Updated Task",
  "description": "Updated description",
  "status": "выполнено",
  "dueDate": "2024-09-15"
}

Так же от себя добавил парочку улучшений которых нет в тз

1)Реализована валидация входных данных (например, проверка обязательных полей, формат даты и прочее).
2)Предусмотрена обработка ошибок, таких как задачи с несуществующими ID или некорректные запросы.

По итогу всё что было необходимо для тз было выполнено)
✅ Java 21+, Spring Boot 3+, Spring Data JPA
✅ Получение выходных дней с внешнего API и кэширование
✅ Создание задачи с валидацией по дате завершения
✅ Получение списка задач с фильтрацией
✅ Обновление задачи
✅ Удаление задачи и обработка ошибок
✅ Swagger/OpenAPI документация
✅ Unit тесты



