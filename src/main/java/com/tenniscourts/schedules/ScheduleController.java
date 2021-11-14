package com.tenniscourts.schedules;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("schedule")
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

	
    private final ScheduleService scheduleService;

    @PostMapping("/add")
    @ApiOperation ("Add a schedule for a given TennisCourt")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "Added Schedules to the tennisCourt"),
            @ApiResponse(code = 400 , message = "Invalid Object passed.")   	
        })
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
    	return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation("Find Shedules between the given Dates")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "Schedules Found between the given Dates"),
            @ApiResponse(code = 400 , message = "Invalid Object passed."),
            @ApiResponse(code = 404 , message = "No Schedules Found")   	
        })
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestBody FindScheduleRequestDTO findScheduleRequestDTO) {
        System.out.println(findScheduleRequestDTO.toString());
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(findScheduleRequestDTO));
        //return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    
    @GetMapping("/{scheduleId}")
    @ApiOperation("Find Schedule by Id")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "Schedule Found"),
            @ApiResponse(code = 400 , message = "Invalid scheduleId passed."),
            @ApiResponse(code = 404 , message = "Schedule not Found")   	
        })
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
