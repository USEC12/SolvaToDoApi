package com.example.demo.mapper;


import com.example.demo.dto.TaskDto;
import com.example.demo.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
}