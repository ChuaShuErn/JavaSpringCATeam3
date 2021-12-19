package sg.edu.iss.LAPS.utility;

import lombok.Data;

@Data
public class AdminLogin {
	
	private String email;
	private String password;
	
	public AdminLogin()
	{
		super();
	}

}
