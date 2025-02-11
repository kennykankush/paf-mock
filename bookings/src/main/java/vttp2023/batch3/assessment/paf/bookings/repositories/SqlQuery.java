package vttp2023.batch3.assessment.paf.bookings.repositories;

public class SqlQuery {

    public static final String SQL_GET_VACANCY = """
            select vacancy from acc_occupancy where acc_id = ?
            """;

    public static final String SQL_SAVE_BOOKING = """
            insert into reservations (
            resv_id ,name, email, acc_id, arrival_date, duration
            )
            values (
            ?, ?, ?, ?, ?, ?
            )    
            
            """;


    public static final String SQL_UPDATE_VACANCY = """
    UPDATE acc_occupancy
    SET vacancy = ?
    WHERE acc_id = ?
    """;
  
}
