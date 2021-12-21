package sg.edu.iss.LAPS.model;

import java.util.ArrayList;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	
	@Size(min=6, max=20, message = "Please enter a valid username. Your username should contain at least 6 characters and not more than 20 characters.")
	@Column(name="username")
	private String Username;
	
	@Email(message = "Please enter a valid email.")
	@Column(name="email")
	private String email;
	
	@NotEmpty(message = "The Password field is required.")
	@Column(name="password")
	private String Password;
	
	@Pattern(regexp="[A-Z][a-z]+", message = "Please enter a valid first name. The first character must be uppercase.")
	@Column(name="first_name")
	private String FirstName;
	
	@Pattern(regexp="[A-Z][a-z]+", message = "Please enter a valid last name. The first character must be uppercase.")
	@Column(name="last_name")
	private String LastName;
	
	@Min(0)
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

	@OneToMany(targetEntity = LeaveApplied.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
	private List<LeaveApplied> leaveAppliedList;
	@OneToMany(targetEntity = LeaveEntitled.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
	private List<LeaveEntitled> leaveEntitledList=new ArrayList<>();
	
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