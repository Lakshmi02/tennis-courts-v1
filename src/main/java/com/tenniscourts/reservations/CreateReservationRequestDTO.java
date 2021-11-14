package com.tenniscourts.reservations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CreateReservationRequestDTO {

    @NotNull
    @Positive
    private Long guestId;

    @NotNull
    @Positive
    private Long scheduleId;

}
