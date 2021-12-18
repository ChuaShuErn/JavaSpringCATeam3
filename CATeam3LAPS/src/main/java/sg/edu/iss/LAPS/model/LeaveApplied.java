package sg.edu.iss.LAPS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "LeaveApplied")
@Data // = adding @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor

public class LeaveApplied {
    @Id //leaveId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveAppliedId;

    @NotNull //leave applied date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date appliedDate;

    @NotNull //leave start date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date leaveStartDate;

    @NotNull //leave end date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date leaveEndDate;

    private float noOfDays;
    private LocalTime leaveStartTime;
    private LocalTime leaveEndTime;

    @NotNull
    private String leaveReason;

    @Enumerated(EnumType.STRING) // leave status
    private ApprovalStatus approvalStatus;

    private String managerComments;

    @ManyToOne
    private User user;

    @ManyToOne
    private LeaveType leaveType;

    private String workDissemination;

    @OneToOne
    @JoinColumn(name = "overseas_trip_overseas_leave_id")
    private OverseasLeaveDetails overseasTrip;

}
