package sg.edu.iss.LAPS.utility;

import lombok.Data;

@Data
public class StaffLogin {
	
		
		private String email;
		private String password;
		
		public StaffLogin()
		{
			super();
		}

}
