package nl.kmartin.springbootapiboilerplate.features.user.password.reset;

import nl.kmartin.springbootapiboilerplate.features.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_tokens")
@TypeAlias("PasswordToken")
public class PasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Type(type = "uuid-char")
	private UUID token;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;

	@NotNull
	private LocalDateTime expiration;
}
