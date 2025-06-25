package com.pds.partidosapp.service;

/**
 * Interface que define el contrato para el envío de notificaciones por correo electrónico.
 * Las implementaciones concretas deben proporcionar la lógica para enviar correos electrónicos.
 */
public interface IAdapterEmailNotification {

    /**
     * Envía un correo electrónico.
     *
     * @param toEmail Dirección de correo electrónico del destinatario
     * @param subject Asunto del correo electrónico
     * @param content Contenido del correo electrónico (puede ser texto plano o HTML)
     * @param isHtmlContent Indica si el contenido es HTML (true) o texto plano (false)
     * @return true si el correo se envió correctamente, false en caso contrario
     * @throws Exception Si ocurre un error durante el envío del correo
     */
    boolean sendEmail(String toEmail, String subject, String content, boolean isHtmlContent) throws Exception;
}
