package sg.edu.iss.LAPS.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.LAPS.utility.ClaimStatus;

@Entity
@Data
@Table(name = "LeaveEntitled")
@NoArgsConstructor
@AllArgsConstructor
public class LeaveEntitled {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveEntitledId;
    private Integer totalLeave;
    @ManyToOne
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private LeaveType leaveType;

    @Override
    public String toString(){
        return leaveEntitledId + "  " + totalLeave;
    }
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date claimDate;
    
    @Enumerated(EnumType.STRING)
    public ClaimStatus claimStatus;
}
