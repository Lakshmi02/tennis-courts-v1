package com.tenniscourts.reservations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("reservations")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

	
	private final ReservationService reservationService;

	@PostMapping("/add")
    @ApiOperation("Book one or more Reservation")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Reservation Successfully Created"),
        @ApiResponse(code = 400 , message = "Invalid guestID/scheduleID passed."),
        @ApiResponse(code = 404 , message = "Guest/Schedule not Found")   	
    })
    public ResponseEntity<Void> bookOneOrMoreReservation(@RequestBody List <CreateReservationRequestDTO> createReservationRequestDTOList) {
        reservationService.bookOneorMoreReservation(createReservationRequestDTOList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

	@GetMapping("/{reservationId}")
    @ApiOperation("Find a Reservation by reservationID")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Reservation Found"),
        @ApiResponse(code = 400 , message = "Invalid reservationID passed."),
        @ApiResponse(code = 404 , message = "Reservation not Found")   	
    })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
		return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }
	
	@GetMapping("/all")
    @ApiOperation("Find all reservations")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "ReservationList Found"),
        @ApiResponse(code = 404 , message = "No Reservations Found")   	
    })
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
		return ResponseEntity.ok(reservationService.findAllReservations());
    }

	@GetMapping("/history")
    @ApiOperation("Find all historic reservations")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "ReservationList Found"),
        @ApiResponse(code = 404 , message = "No Reservations Found")   	
    })
    public ResponseEntity<List<ReservationDTO>> findHistoricReservations() {
		return ResponseEntity.ok(reservationService.findHistoricReservations());
    }
	
	@DeleteMapping("/cancel/{reservationId}")
    @ApiOperation("Cancel A Reservation")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Cancellation Successful"),
        @ApiResponse(code = 400 , message = "Invalid reservationID passed."),
        @ApiResponse(code = 404 , message = "Reservation not Found")   	
    })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

	@PutMapping("/reschedule")
    @ApiOperation("Reschedule a Reservation")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Reservation rescheduled successfully."),
        @ApiResponse(code = 400 , message = "Invalid reservationId/scheduleId passed."),
        @ApiResponse(code = 404 , message = "Reservation not Found")   	
    })
	  public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody RescheduleReservationRequestDTO rescheduleReservationRequestDTO) {
		return ResponseEntity.ok(reservationService.rescheduleReservation(rescheduleReservationRequestDTO));
				}
	
	@PutMapping("/refundUponCompletion/{reservationId}")
    @ApiOperation("Admin - Registration Deposit fee 100% refund on match completion")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Refund Successful"),
        @ApiResponse(code = 400 , message = "Invalid reservationId passed."),
        @ApiResponse(code = 404 , message = "Reservation not Found")   	
    })
	  public ResponseEntity<ReservationDTO> refundUponCompletion(@PathVariable Long reservationId) {
		return ResponseEntity.ok(reservationService.refundUponCompletion(reservationId));
				}

	@PutMapping("/deductOnAbsence/{reservationId}")
    @ApiOperation("Admin - Registration Deposit fee 100% deduction if User does not show up for match")
    @ApiResponses(value = { 
    	@ApiResponse(code = 200 , message = "Deduction Successful"),
        @ApiResponse(code = 400 , message = "Invalid registrationId passed."),
        @ApiResponse(code = 404 , message = "Reservation not Found")   	
    })
	  public ResponseEntity<ReservationDTO> deductOnAbsence(@PathVariable Long reservationId) {
		return ResponseEntity.ok(reservationService.refundUponCompletion(reservationId));
				}
}
