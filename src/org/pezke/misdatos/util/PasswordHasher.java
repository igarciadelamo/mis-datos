package org.pezke.misdatos.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;


/**
 * Implementación simple de métodos de utilidad para generar códigos <code>hash</code> con
 * <code>salt</code>.
 * <p>
 * Esta clase utiliza los siguientes parámetros por defecto:
 * <ul>
 * <li>Algoritmo de hash: HmacSHA512</li>
 * <li>Longitud del <code>salt</code>: 64 bytes</li>
 * <li>Iteraciones: 1000</li>
 * <li>Conjunto de caracteres para codificación de cadenas: UTF-8</li>
 * </ul>
 * <p>
 * Ejemplo:
 * 
 * <pre>
 * // Crea el hash de &lt;code&gt;valor&lt;/code&gt;
 * PasswordHash ph = PasswordHasher.hash(valor);
 * // Comprueba si un &lt;code&gt;valor&lt;/code&gt; concuerda con un &lt;code&gt;hash&lt;/code&gt;
 * boolean valido = PasswordHasher.isValid(valor, ph.getHash(), ph.getSalt());
 * </pre>
 * <p>
 * NOTA: esta clase es un ejemplo de implementación, aunque puede usarse en un entorno de
 * producción. Para simplificar su uso todas las excepciones chequeadas se capturan y se relanzan
 * como {@link RuntimeException}. En una implementación más completa podría ser conveniente definir
 * excepciones específicas.
 * 
 * @author Jose Manuel Cejudo Gausi
 */
public final class PasswordHasher {

    /**
     * Nombre del algoritmo de hash.
     */
    public static final String ALGORITHM = "HmacSHA512";

    /**
     * Longitud del <code>salt</code>. Igual al tamaño del resultado del algoritmo.
     */
    public static final int SALT_LENGTH = 64;

    /**
     * Número de iteraciones a realizar.
     */
    public static final int ITERATIONS = 10;

   
    /**
     * Constructor privado para impedir la instanciación de esta clase de utilidad.
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
     * Calcula el <code>hash</code> del valor indicado usando el <code>salt</code> indicado.
     * <p>
     * El objeto {@link PasswordHash} devuelto contiene el <code>hash</code> calculado y el
     * <code>salt</code> generado.
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
     * Comprueba si el <code>hash</code> calculado para el valor y el <code>salt</code> indicados es
     * igual al <code>hash</code> correcto indicado.
     * <p>
     * Este método permite comprobar si un valor es integro con respecto al <code>hash</code>.
     * 
     * @param value
     *            el valor a comprobar.
     * @param correctHash
     *            el <code>hash</code> correcto.
     * @param salt
     *            el <code>salt</code> a utilizar para la generación del <code>hash</code> de
     *            <code>value</code>. Debe ser el mismo que el utilizado para calcular
     *            <code>hash</code>.
     * @return <code>true</code> si el <code>hash</code> de <code>value</code> usando
     *         <code>salt</code> es igual a <code>correctHash</code>.
     */
    public static boolean isValid(final String value, final String correctHash, final String correctSalt) {
    	final Password ph = hash(value, correctSalt);
    	boolean result = correctHash.equals(ph.getHash());
    	return result;
    }

}