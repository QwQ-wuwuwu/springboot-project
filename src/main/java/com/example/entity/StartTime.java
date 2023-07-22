package com.example.entity;

import lombok.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class StartTime {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}