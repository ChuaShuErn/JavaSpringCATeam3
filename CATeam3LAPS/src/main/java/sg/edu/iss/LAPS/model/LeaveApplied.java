package sg.edu.iss.LAPS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "LeaveApplied")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveAppliedId;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private LocalTime leaveStartTime;
    private LocalTime leaveEndTime;
    private LeaveType leaveType;
    private String LeaveReason;
    private ApprovalStatus approvalStatus;
    private String ManagerComments;
    @ManyToOne
    private User user;
}
