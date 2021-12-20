package sg.edu.iss.LAPS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	
	@Column(name="role_name", unique=true, nullable=false)
	@NotEmpty
	private String name;
	
	@Column(name="role_desc")
	@NotEmpty
	private String description;

	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	

}
