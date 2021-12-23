package sg.edu.iss.LAPS.model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @OneToOne(mappedBy = "overseasTrip", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private LeaveApplied leaveApplied;
}
