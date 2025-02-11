package vttp2023.batch3.assessment.paf.bookings.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    private String resv_id;
    private String name;
    private String email;
    private String acc_id;
    private Date arrivalDate;
    private int duration;
    
}
