package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accomodation {

    @NotNull(message = "Please select a country")
    private String country;

    @Min(value = 1, message="Must be between 1-10")
    @Max(value = 10, message="Must be between 1-10")
    private int size;

    @Min(value = 1, message="Must be between 1-10000")
    private double minPrice;

    @Max(value = 10000, message="Must be between 1-10000")
    private double maxPrice;
    
}
