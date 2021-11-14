package com.tenniscourts.tenniscourts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("tenniscourt")
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {


    private final TennisCourtService tennisCourtService;

    @PostMapping("/add")
    @ApiOperation("Add a new Tennis Court")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "Tenniscourt added successfully."),
            @ApiResponse(code = 400 , message = "Invalid details passed.")  	
        })
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a Tennis Court by Tennis Court ID")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "Tenniscourt Found"),
            @ApiResponse(code = 400 , message = "Invalid tenniscourtID passed."),
            @ApiResponse(code = 404 , message = "Tenniscourt not Found")   	
        })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation("Find a Tennis Court with All Schedules")
    @ApiResponses(value = { 
        	@ApiResponse(code = 200 , message = "TennisCourt with Schedules Found"),
            @ApiResponse(code = 400 , message = "Invalid tennisCourtId passed."),
            @ApiResponse(code = 404 , message = "Tenniscourt not Found")   	
        })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
