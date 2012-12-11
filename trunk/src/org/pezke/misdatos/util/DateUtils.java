package org.pezke.misdatos.util;


import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

	
	
	/**
	 * Formato de la fecha estandar incluyendo la hora exacta
	 */
	private static final String STANDARD_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	/**
	 * Formato de la fecha estandar sin incluir la hora
	 */
	private static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";


	/**
	 * Metodo que formatea uuna fecha de tipo Date, devolviendo su
	 * representacion como cadena de texto.
	 * 
	 * @param date
	 *            Objeto Date con la fecha que se desea formatear
	 * 
	 * @return String con la fecha con el formato pedido
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat standartFormat = new SimpleDateFormat(STANDARD_DATE_FORMAT);
		String result = standartFormat.format(date);
		return result;
	}
	
	/**
	 * Metodo que formatea uuna fecha de tipo Date, devolviendo su
	 * representacion como cadena de texto incluyendo la hora.
	 * 
	 * @param date
	 *            Objeto Date con la fecha que se desea formatear
	 * 
	 * @return String con la fecha con el formato pedido
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
		String result = sdf.format(date);
		return result;
	}
	
	
}
