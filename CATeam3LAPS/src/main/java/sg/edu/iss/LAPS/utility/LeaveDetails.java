package sg.edu.iss.LAPS.utility;

import lombok.Data;

@Data
public class LeaveDetails {
    private String Name;
    private Integer Pending;
    private Integer Taken;
    private Float Available;
}
