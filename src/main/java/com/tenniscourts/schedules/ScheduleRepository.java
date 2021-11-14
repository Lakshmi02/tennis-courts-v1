package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	List<Schedule> findByStartDateTimeAfterAndEndDateTimeBefore(LocalDateTime startDate , LocalDateTime endDate);
    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
}