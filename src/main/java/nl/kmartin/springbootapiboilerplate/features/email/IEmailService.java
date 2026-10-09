package nl.kmartin.springbootapiboilerplate.features.email;

import nl.kmartin.springbootapiboilerplate.features.user.User;
import nl.kmartin.springbootapiboilerplate.features.user.password.reset.PasswordToken;

import javax.mail.MessagingException;


public interface IEmailService {
	void sendForgotPasswordEmail(User user, PasswordToken passwordToken) throws MessagingException;
}
