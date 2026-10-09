package nl.kmartin.springbootapiboilerplate.features.user;

import nl.kmartin.springbootapiboilerplate.features.user.password.change.ChangePasswordDto;
import nl.kmartin.springbootapiboilerplate.features.user.password.forgot.ForgotPasswordDto;
import nl.kmartin.springbootapiboilerplate.features.user.password.PasswordDto;
import nl.kmartin.springbootapiboilerplate.features.user.password.reset.ResetPasswordDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IUserService {

	@Validated({User.Create.class})
	User saveUser(@Valid User user);

	@PreAuthorize("isAuthenticated()")
	User getAuthenticatedUser();

	@Validated({User.Update.class})
	@PreAuthorize("isAuthenticated()")
	User updateUser(@Valid User user);

	@PreAuthorize("isAuthenticated()")
	void deleteUser(@Valid PasswordDto passwordDto);

	@PreAuthorize("isAuthenticated()")
	void changePassword(@Valid ChangePasswordDto changePasswordDto);

	void forgotPassword(@Valid ForgotPasswordDto forgotPasswordDto);

	void resetPassword(@Valid ResetPasswordDto resetPasswordDto);

}
