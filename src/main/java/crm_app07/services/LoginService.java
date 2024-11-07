package crm_app07.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import crm_app07.entity.UserEntity;
import crm_app07.repository.UserRepository;
import crm_app07.utils.PasswordUtils;

public class LoginService {
	private UserRepository userRepository = new UserRepository();

	public UserEntity authenticateUser(String email, String password) throws SQLException {
		UserEntity user = userRepository.getUserByEmail(email);
		if (user != null) {
			String storedPassword = user.getPassword();
			if (verifyPassword(password, storedPassword)) {
				return user;
			}
		}
		return null;
	}

	public boolean verifyPassword(String inputPassword, String storedPassword) {
		try {
			String[] parts = storedPassword.split(":");
			byte[] salt = Base64.getDecoder().decode(parts[0]);
			byte[] hash = Base64.getDecoder().decode(parts[1]);

			KeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, PasswordUtils.getIterations(),
					PasswordUtils.getKeyLength());
			SecretKeyFactory factory = SecretKeyFactory.getInstance(PasswordUtils.getAlgorithm());
			byte[] testHash = factory.generateSecret(spec).getEncoded();

			return Arrays.equals(hash, testHash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		}
	}
}
