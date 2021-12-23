package sg.edu.iss.LAPS.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "LeaveEntitled")
@NoArgsConstructor
@AllArgsConstructor
public class LeaveEntitled {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveEntitledId;
    private Float totalLeave;
    @ManyToOne
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private LeaveType leaveType;

    @Override
    public String toString(){
        return leaveEntitledId + "  " + totalLeave;
    }
}
