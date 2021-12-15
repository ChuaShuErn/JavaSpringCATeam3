package sg.edu.iss.LAPS.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "OverseasLeave")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverseasLeaveDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer overseasLeaveId;
    private String country;
    private String city;
    private Integer phone;
}
