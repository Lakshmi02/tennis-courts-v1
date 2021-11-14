package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ScheduleRepository scheduleRepository;

    public void bookOneorMoreReservation(List<CreateReservationRequestDTO> createReservationRequestDTOList) {
    List<ReservationDTO> reservationDTO = new ArrayList<ReservationDTO>();
    	for(CreateReservationRequestDTO request:createReservationRequestDTOList) {
    		reservationDTO.add(this.bookReservation(request));
    	}
    	}
    
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
    	Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).<EntityNotFoundException>orElseThrow(() -> {
    	throw new EntityNotFoundException("No Guest found for the Given ID");
    });
    
    Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).<EntityNotFoundException>orElseThrow(() -> {
    	throw new EntityNotFoundException("No Schedule found for the Given ID");
    });
    
    if(schedule.getStartDateTime().isBefore(LocalDateTime.now())) {
    	throw new BusinessException("Reservation Cannot be booked for an old date-time");
    }
    List<Reservation> reservationList = reservationRepository.findBySchedule_Id(schedule.getId());
    for(Reservation res:reservationList) {
    		if(res.getReservationStatus().equals(ReservationStatus.READY_TO_PLAY)) {
    			throw new AlreadyExistsEntityException("This TennisCourt is already booked for the given slot");
    		}	
    }
    Reservation reservation = Reservation.builder()
    		.guest(guest)
    		.schedule(schedule)
    		.reservationStatus(ReservationStatus.READY_TO_PLAY)
    		.value(BigDecimal.valueOf(10))
    		.build();
    	return reservationMapper.map(reservationRepository.saveAndFlush(reservation));
    }
    

    public ReservationDTO findReservation(Long reservationId) {
    	System.out.println("ServiceLayer: ReservationID: " + reservationId); 
    	return reservationRepository.findById(reservationId).map(reservationMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);
        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule/complete because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule/complete only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        
        if (hours > 24) {
            return reservation.getValue();
        }
        else 
        	if (hours >=12 && hours <= 24) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
        }
        else if(hours >=2 && hours < 12) {
        	return reservation.getValue().multiply(BigDecimal.valueOf(0.50));
        }
        else if(hours < 2 && minutes >=1 ) {
        	return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
        }
        else if(reservation.getReservationStatus().equals(ReservationStatus.COMPLETED)) {
        	return reservation.getValue();
        }
        else if(reservation.getReservationStatus().equals(ReservationStatus.ABSENT)) {
        	return BigDecimal.ZERO;
        }
        else
        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
	
	  public ReservationDTO rescheduleReservation(RescheduleReservationRequestDTO rescheduleReservationRequestDTO) { 
      Reservation previousReservation = cancel(rescheduleReservationRequestDTO.getReservationId());
	  
	  if (rescheduleReservationRequestDTO.getScheduleId().equals(previousReservation.getSchedule().getId())) { throw new
	  IllegalArgumentException("Cannot reschedule to the same slot."); }
	  
	  previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
	  reservationRepository.save(previousReservation);
	  
	  ReservationDTO newReservation =
	  bookReservation(CreateReservationRequestDTO.builder()
	  .guestId(previousReservation.getGuest().getId()) .scheduleId(rescheduleReservationRequestDTO.getScheduleId())
	  .build()); newReservation.setPreviousReservation(reservationMapper.map(
	  previousReservation)); return newReservation; }

	public List<ReservationDTO> findAllReservations() {
		List<Reservation> allReservations = reservationRepository.findAll();
		if(!allReservations.isEmpty()) {
			return reservationMapper.map(allReservations);
		} else {
			throw new EntityNotFoundException("No Reservations found.");
		}
	}


	public ReservationDTO refundUponCompletion(Long reservationId) {
		Reservation reservation = reservationMapper.map(this.findReservation(reservationId));
		this.validateCancellation(reservation);
		reservation.setReservationStatus(ReservationStatus.COMPLETED);
		reservation.setRefundValue(getRefundValue(reservation));
		reservation.setValue(reservation.getValue().subtract(getRefundValue(reservation)));
		reservationRepository.saveAndFlush(reservation);
		return reservationMapper.map(reservation);
	}


	public ReservationDTO deductOnAbsence(Long reservationId) {
		Reservation reservation = reservationMapper.map(this.findReservation(reservationId));
		this.validateCancellation(reservation);
		reservation.setReservationStatus(ReservationStatus.ABSENT);
		reservation.setRefundValue(getRefundValue(reservation));
		reservation.setValue(reservation.getValue().subtract(getRefundValue(reservation)));
		reservationRepository.saveAndFlush(reservation);
		return reservationMapper.map(reservation);
	}


	public List<ReservationDTO> findHistoricReservations() {
		LocalDateTime now = LocalDateTime.now();
		List<Reservation> allReservations = reservationRepository.findAllBySchedule_StartDateTimeBefore(now);
		if(!allReservations.isEmpty()) {
			return reservationMapper.map(allReservations);
		} else {
			throw new EntityNotFoundException("No Reservations found.");
		}
	}
	 
}
