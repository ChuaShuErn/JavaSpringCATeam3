package sg.edu.iss.LAPS.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private LeaveType leaveType;
}
