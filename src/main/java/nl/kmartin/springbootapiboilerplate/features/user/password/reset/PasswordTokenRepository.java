package nl.kmartin.springbootapiboilerplate.features.user.password.reset;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Long> {

	Optional<PasswordToken> findByToken(UUID token);

}
