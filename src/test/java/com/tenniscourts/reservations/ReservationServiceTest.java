package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.tenniscourts.schedules.Schedule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {
	
	public ReservationServiceTest() {
		
	}
	
    @InjectMocks
    ReservationService reservationService;
  
    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);
        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }
    
    @Test
    public void getRefundValue75Percent() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(23).plusMinutes(59);
        schedule.setStartDateTime(startDateTime);
        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()).compareTo(new BigDecimal(7.5)) == 0);
    }
    
    @Test
    public void getRefundValue50Percent() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(11).plusMinutes(59);
        schedule.setStartDateTime(startDateTime);
        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()).compareTo(new BigDecimal(5)) == 0);
    }
    
    @Test
    public void getRefundValue25Percent() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(01).plusSeconds(10);
        schedule.setStartDateTime(startDateTime);

        Assert.assertTrue(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()).compareTo(new BigDecimal(2.5)) == 0);
    }
}
