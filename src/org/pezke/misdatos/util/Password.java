package org.pezke.misdatos.util;

/**
 * Representa el resultado de una operación de <code>hash</code> con <code>salt</code>.
 */
public class Password {

    /**
     * Contiene el valor de <code>hash</code>.
     */
    private final String hash;

    /**
     * Contiene el valor de <code>salt</code>.
     */
    private final String salt;

    /**
     * Construye una nueva instancia con los valores indicados.
     * 
     * @param hash
     *            el valor de <code>hash</code>.
     * @param salt
     *            el valor de <code>salt</code>.
     */
    public Password(final String hash, final String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    /**
     * Devuelve el valor de <code>hash</code>.
     * 
     * @return el valor.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Devuelve el valor de <code>salt</code>.
     * 
     * @return el valor.
     */
    public String getSalt() {
        return salt;
    }

}