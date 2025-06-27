//package com.pds.partidosapp.config;
//
//import com.pds.partidosapp.service.EstrategiaDeNotificacion;
//import com.pds.partidosapp.service.NotificadorService;
//import com.pds.partidosapp.service.NotificacionPorMail;
//import com.pds.partidosapp.service.AdapterJavaMail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import jakarta.annotation.PostConstruct;
//
///**
// * Configuración para el servicio de notificaciones.
// * Configura la estrategia de notificación por defecto (correo electrónico).
// */
//@Configuration
//public class PartidoObserverConfig {
//
//    private final NotificadorService notificadorService;
//    private final JavaMailSender javaMailSender;
//
//    @Autowired
//    public PartidoObserverConfig(NotificadorService notificadorService,
//                               JavaMailSender javaMailSender) {
//        this.notificadorService = notificadorService;
//        this.javaMailSender = javaMailSender;
//    }
//
//    @Bean
//    public AdapterJavaMail adapterJavaMail() {
//        return new AdapterJavaMail(javaMailSender);
//    }
//
//    @Bean
//    public EstrategiaDeNotificacion notificacionPorMail(AdapterJavaMail adapterJavaMail) {
//        return new NotificacionPorMail(adapterJavaMail);
//    }
//
//    /**
//     * Configura la estrategia de notificación por defecto al iniciar la aplicación.
//     * En este caso, se configura el envío de notificaciones por correo electrónico.
//     */
//    @PostConstruct
//    public void init() {
//        // Configurar la estrategia de notificación por correo
//        AdapterJavaMail adapter = adapterJavaMail();
//        EstrategiaDeNotificacion estrategia = new NotificacionPorMail(adapter);
//        notificadorService.cambiarEstrategiaDeNotificacion(estrategia);
//
//        System.out.println("Configuración del servicio de notificaciones inicializada");
//    }
//}
