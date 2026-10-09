package nl.kmartin.springbootapiboilerplate.security.authorizationserver.clientdetails;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientDetailsRepository extends CrudRepository<ClientDetailsImpl, String> {
	Optional<ClientDetailsImpl> findByClientId(String clientId);
}
