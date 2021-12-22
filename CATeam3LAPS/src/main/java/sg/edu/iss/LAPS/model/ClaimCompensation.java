package sg.edu.iss.LAPS.model;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.LAPS.utility.ClaimStatus;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Entity
@Table(name = "ClaimCompensation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimCompensation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compensationClaimId;
	
	@ManyToOne
    private User user;
	
	@NotNull
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date claimDate;
	
	@NotNull
	private String compensationReason;
	
	@NotNull
	private float daysRequested;
	

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

}