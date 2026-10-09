package nl.kmartin.springbootapiboilerplate.features.user;

import nl.kmartin.springbootapiboilerplate.validators.nowhitespace.NoWhitespace;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(groups = {Create.class, Update.class})
	@Length(groups = {Create.class, Update.class}, min = 4, max = 24)
	@NoWhitespace(groups = {Create.class, Update.class})
	private String userName;

	@NotBlank(groups = {Create.class, Update.class})
	private String firstName;

	@NotBlank(groups = {Create.class, Update.class})
	private String lastName;

	@NotBlank(groups = {Create.class, Update.class})
	@Email(groups = {Create.class, Update.class})
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(groups = {Create.class, Update.class})
	@Length(groups = {Create.class, Update.class}, min = 4, max = 24)
	private String password;

	public interface Create {
	}

	public interface Update {
	}

}