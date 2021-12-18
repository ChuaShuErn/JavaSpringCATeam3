package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;

import sg.edu.iss.LAPS.model.User;

public class UserSession {

	private static final long serialVersionUID = 1L;
	private User user = null;
	private ArrayList<User> subordinates = null;
	
	public UserSession() {
		super();
	}
   //String sessionId,
	public UserSession(User user, ArrayList<User> subordinates) {
		super();
		//this.sessionId = sessionId;
		this.user = user;
		this.subordinates = subordinates;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<User> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(ArrayList<User> subordinates) {
		this.subordinates = subordinates;
	}



}