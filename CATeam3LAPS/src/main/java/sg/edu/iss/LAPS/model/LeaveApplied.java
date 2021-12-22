package sg.edu.iss.LAPS.model;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Entity
@Table(name = "LeaveApplied")
@Data
// = adding @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplied {
    @Id //leaveId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveAppliedId;

    //@NotNull //leave applied date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appliedDate;

    @NotNull //leave start date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveStartDate;

    @NotNull //leave end date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveEndDate;

    private float noOfDays;
    private LocalTime leaveStartTime;
    private LocalTime leaveEndTime;

    @NotNull
    private String leaveReason;

    @Enumerated(EnumType.STRING) // leave status
    private LeaveStatus approvalStatus;

    private String managerComments;

    @ManyToOne
    private User user;

    @ManyToOne
    private LeaveType leaveType;

    private String workDissemination;

    private boolean isOverseas;

    @OneToOne(cascade = CascadeType.ALL )
    private OverseasLeaveDetails overseasTrip;

    public boolean getIsOverseas() {
        return isOverseas;
    }

    public void setIsOverseas(boolean overseas) {
        isOverseas = overseas;
    }
}
