package com.example.demo.service;

import com.example.demo.dto.TaskDto;
import com.example.demo.exception.TaskValidationException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final HolidayService holidayService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, HolidayService holidayService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.holidayService = holidayService;
    }

    public List<TaskDto> getAllTasks(String status) {
        List<Task> tasks = (status != null) ? taskRepository.findByStatus(status) : taskRepository.findAll();
        return tasks.stream().map(taskMapper::toDto).toList();
    }

    public TaskDto createTask(TaskDto taskDto) throws TaskValidationException {
        LocalDate dueDate = taskDto.getDueDate();
        List<LocalDate> holidays = holidayService.getHolidays("RU", 2024);

        if (holidays.contains(dueDate)) {
            LocalDate nextAvailableDate = findNextWorkingDay(dueDate, holidays);
            throw new TaskValidationException("Выберите другой день — ближайший доступный " + nextAvailableDate);
        }

        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) throws Exception {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task not found with id: " + id));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setDueDate(taskDto.getDueDate());
        existingTask.setStatus(taskDto.getStatus());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }
    public void deleteTask(Long id) throws Exception {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task not found with id: " + id));

        taskRepository.delete(task);
    }

    private LocalDate findNextWorkingDay(LocalDate date, List<LocalDate> holidays) {
        while (holidays.contains(date)) {
            date = date.plusDays(1);
        }
        return date;
    }
}