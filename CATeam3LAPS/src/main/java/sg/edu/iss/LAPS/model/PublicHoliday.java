package sg.edu.iss.LAPS.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PublicHolidays")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer holidayId;
    private String holidayName;
    private Date holidayStartDate;
    private Date holidayEndDate;

}
