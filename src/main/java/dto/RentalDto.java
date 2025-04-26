package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {

    private Long id;
    private Long clientId;
    private Long equipmentId;
    private String rentalDate;
    private String returnDate;
    private String status;
}
