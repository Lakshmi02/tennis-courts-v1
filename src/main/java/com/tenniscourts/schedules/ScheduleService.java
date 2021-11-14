package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	
    private final ScheduleRepository scheduleRepository;
	
    private final TennisCourtRepository tennisCourtRepository;
    
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt court = tennisCourtRepository.findById(createScheduleRequestDTO.getTennisCourtId()).<EntityNotFoundException>orElseThrow(() -> {
        	throw new EntityNotFoundException("Tennis Court doesnt Exist");
        	});
        Schedule schedule = new Schedule();
        schedule.setTennisCourt(court);
        if(createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())) {
        	throw new BusinessException("Cannot create Slots for Older date and Time");
        }
        schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        schedule.setEndDateTime(createScheduleRequestDTO.getEndDateTime());
        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(FindScheduleRequestDTO findScheduleRequestDTO) {
        if(findScheduleRequestDTO.getStartDate().isAfter(findScheduleRequestDTO.getEndDate())) {
        	throw new BusinessException("StartDate is After End Date.");
        }
        LocalDateTime startDateTime = LocalDateTime.of(findScheduleRequestDTO.getStartDate(), LocalTime.of(0, 0));
        LocalDateTime endDateTime = LocalDateTime.of(findScheduleRequestDTO.getEndDate(), LocalTime.of(0, 0));
        
        	return scheduleMapper.map(scheduleRepository.findByStartDateTimeAfterAndEndDateTimeBefore(startDateTime, endDateTime));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
    	return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() ->{
    		throw new EntityNotFoundException("Schedule not Found");
    	});
    	}

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
