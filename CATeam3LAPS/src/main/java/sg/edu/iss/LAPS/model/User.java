package sg.edu.iss.LAPS.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="user")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="username")
	private String Username;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String Password;
	
	@Column(name="first_name")
	private String FirstName;
	
	@Column(name="last_name")
	private String LastName;
	
	@Column(name="reportsTo")
	private Integer ReportsTo;
	
	@ManyToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "role_id"))
	private List<Role> roles=new ArrayList<>();

	@OneToMany(targetEntity = LeaveApplied.class,cascade = CascadeType.ALL,mappedBy = "user")
	private List<LeaveApplied> leaveAppliedList;
	@OneToMany(targetEntity = LeaveEntitled.class,cascade = CascadeType.ALL,mappedBy = "user")
	private List<LeaveEntitled> leaveEntitledList;
	
	
	public User(String username, 
			String email, 
			String password,
			String firstName, 
			String lastName, 
			Integer reportsTo) {
		super();
		Username = username;
		this.email = email;
		Password = password;
		FirstName = firstName;
		LastName = lastName;
		ReportsTo = reportsTo;
	}
	
}