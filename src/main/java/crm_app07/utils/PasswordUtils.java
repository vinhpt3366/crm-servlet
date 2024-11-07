package crm_app07.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 128;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

	public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, getIterations(), getKeyLength());
		SecretKeyFactory factory = SecretKeyFactory.getInstance(getAlgorithm());

		byte[] hash = factory.generateSecret(spec).getEncoded();
		return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
	}

	public static int getKeyLength() {
		return KEY_LENGTH;
	}

	public static int getIterations() {
		return ITERATIONS;
	}

	public static String getAlgorithm() {
		return ALGORITHM;
	}
}
