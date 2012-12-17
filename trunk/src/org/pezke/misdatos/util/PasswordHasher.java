package org.pezke.misdatos.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.model.User;

import android.util.Base64;

/**
 * Implementación simple de métodos de utilidad para generar códigos
 * <code>hash</code> con <code>salt</code>.
 */
public final class PasswordHasher {

	/**
	 * Algorithm name
	 */
	public static final String ALGORITHM = "HmacSHA512";

	/**
	 * Salt length
	 */
	public static final int SALT_LENGTH = 128;

	/**
	 * Key length
	 */
	public static final int KEY_LENGTH = 256;

	/**
	 * Número de iteraciones a realizar.
	 */
	public static final int ITERATIONS = 10;

	/**
	 * Constructor privado para impedir la instanciación de esta clase de
	 * utilidad.
	 */
	private PasswordHasher() {
		super();
	}

	/**
	 * Calcular el SALT
	 */
	public static String getSalt() {
		final byte[] salt = new byte[SALT_LENGTH];
		final SecureRandom rnd = new SecureRandom();
		rnd.nextBytes(salt);
		String result = new String(Base64.encode(salt, Base64.DEFAULT));
		return result;
	}

	/**
	 * Calcula el <code>hash</code> del valor indicado usando el
	 * <code>salt</code> indicado.
	 * <p>
	 * El objeto {@link PasswordHash} devuelto contiene el <code>hash</code>
	 * calculado y el <code>salt</code> generado.
	 * 
	 * @param value
	 *            el valor para el que calcular el <code>hash</code>.
	 * @param salt
	 *            el valor de <code>salt</code> a utilizar.
	 * @return el resultado del cálculo <code>hash</code>.
	 */
	public static Password hash(final String value, String salt) {

		try {
			byte[] retVal;
			final byte[] valueBytes = value.getBytes();
			final byte[] saltBytes = Base64.decode(salt.getBytes(), Base64.DEFAULT);
			final Mac mac = Mac.getInstance(ALGORITHM);
			final Key key = new SecretKeySpec(saltBytes, ALGORITHM);
			mac.init(key);
			retVal = mac.doFinal(valueBytes);
			for (int i = 1; i < ITERATIONS; i++) {
				retVal = mac.doFinal(retVal);
			}
			String shash = new String(Base64.encode(retVal, Base64.DEFAULT));
			Password result = new Password(shash, salt);
			return result;

		} catch (final NoSuchAlgorithmException cause) {
			throw new RuntimeException(cause);
		} catch (final InvalidKeyException cause) {
			throw new RuntimeException(cause);
		}
	}

	/**
	 * Comprueba si el <code>hash</code> calculado para el valor y el
	 * <code>salt</code> indicados es igual al <code>hash</code> correcto
	 * indicado.
	 * <p>
	 * Este método permite comprobar si un valor es integro con respecto al
	 * <code>hash</code>.
	 * 
	 * @param value
	 *            el valor a comprobar.
	 * @param correctHash
	 *            el <code>hash</code> correcto.
	 * @param salt
	 *            el <code>salt</code> a utilizar para la generación del
	 *            <code>hash</code> de <code>value</code>. Debe ser el mismo que
	 *            el utilizado para calcular <code>hash</code>.
	 * @return <code>true</code> si el <code>hash</code> de <code>value</code>
	 *         usando <code>salt</code> es igual a <code>correctHash</code>.
	 */
	public static boolean isValid(final String value, final String correctHash,
			final String correctSalt) {
		final Password ph = hash(value, correctSalt);
		boolean result = correctHash.equals(ph.getHash());
		return result;
	}

	/**
	 * Crypting the password
	 */
	public static Password crypt(final User user, final String password) {

		try {
			
			//Secret Key
			SecretKey key = getSecretKey(user);

			//Crypting
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = new byte[cipher.getBlockSize()];
			SecureRandom random = new SecureRandom();
			random.nextBytes(iv);
			IvParameterSpec ivParams = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
			byte[] ciphertext = cipher.doFinal(password.getBytes());
		
			//Preparing the response
			String siv = new String(Base64.encode(iv, Base64.DEFAULT));
			String shash = new String(Base64.encode(ciphertext, Base64.DEFAULT));
			Password result = new Password(shash, siv);
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Decrypting the password
	 */
	public static String decrypt(final User user, final Data data) {

		try {
			//Secret Key
			SecretKey key = getSecretKey(user);
			
			//Data
			final byte[] iv = Base64.decode(data.getSalt().getBytes(), Base64.DEFAULT);
			final byte[] password = Base64.decode(data.getValue().getBytes(), Base64.DEFAULT);

			//Decrypting
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParams = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
			byte[] plaintext = cipher.doFinal(password);
			String result = new String(plaintext);
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the SecretKey
	 */
	private static SecretKey getSecretKey(final User user)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		String salt = user.getPassword();
		String privateKey = user.getSalt();
		final byte[] saltBytes = Base64.decode(salt.getBytes(), Base64.DEFAULT);
		
		KeySpec keySpec = new PBEKeySpec(privateKey.toCharArray(), saltBytes, ITERATIONS, SALT_LENGTH);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHSHAAND128BITAES-CBC-BC");

		SecretKey secret = keyFactory.generateSecret(keySpec);
		return secret;
	}
	
	

}