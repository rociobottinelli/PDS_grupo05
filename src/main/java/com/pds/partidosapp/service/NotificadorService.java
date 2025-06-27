package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.InvitacionRequestDTO;
import com.pds.partidosapp.dto.UsuarioDTO;
import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import com.pds.partidosapp.model.Notificacion;
import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para el manejo de notificaciones.
 * Permite enviar notificaciones utilizando diferentes estrategias.
 */
@Service
@Transactional
public class NotificadorService implements INotificadorService, Observer {

    private final IAdapterEmailNotification adapter;
    private EstrategiaDeNotificacion estrategiaDeNotificacion;
    private final static String ENDPOINT = "http://localhost:8081/api/invitaciones";

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IInvitacionService invitacionService;

    /**
     * Constructor que inyecta el adaptador de notificación por correo electrónico.
     * 
     * @param adapter Adaptador para el envío de correos electrónicos
     */
    @Autowired(required = false)
    public NotificadorService(IAdapterEmailNotification adapter, IUsuarioService usuarioService) {
        this.adapter = adapter;
        this.usuarioService = usuarioService;
    }

    /**
     * Envía una notificación utilizando la estrategia configurada.
     * Si no se ha configurado ninguna estrategia, no se realizará ninguna acción.
     * 
     * @param notificacion La notificación a enviar
     */
    @Override
    public void enviar(Notificacion notificacion) {
        //if (estrategiaDeNotificacion != null) {
        //}
        cambiarEstrategiaDeNotificacion(new NotificacionPorMail(adapter));
        estrategiaDeNotificacion.enviar(notificacion);

        cambiarEstrategiaDeNotificacion(new NotificarPorFirebase());
        estrategiaDeNotificacion.enviar(notificacion);
    }

    /**
     * Cambia la estrategia de notificación que se utilizará para enviar las notificaciones.
     * 
     * @param estrategia La nueva estrategia de notificación a utilizar
     */
    @Override
    public void cambiarEstrategiaDeNotificacion(EstrategiaDeNotificacion estrategia) {
        this.estrategiaDeNotificacion = estrategia;
    }
    
    @Override
    public void update(Partido partido) {

        //i. Se cree un partido nuevo para su deporte favorito.
        switch (partido.getEstado()){
            case "NECESITAMOS_JUGADORES" -> {
                usuarioService.findUsuariosByDeporte(partido.getDeporte().getId(), null).forEach(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getNombreUsuario(), null, usuario.getEmail());

                    var invitacion = invitacionService.crear(new InvitacionRequestDTO(partido.getId(), usuario.getId(), EstadoInvitacionEnum.PENDIENTE));

                    String params = "invitacionId=" + invitacion.getId();

                    String msj = "Se creo un nuevo partido para tu deporte favorito.";
                    msj += "\nAceptar invitacion: " + ENDPOINT + "/aceptar?" +  params;
                    msj += "\nCancelar invitacion:" + ENDPOINT + "/rechazar?"+ params;

                    Notificacion notificacion = new Notificacion(usuarioDTO, msj);
                    enviar(notificacion);
                });
            }

            //ii. Se unan suficientes jugadores y el partido pase a estado "Partido armado".
            //iii. Se confirme el partido.
            //iv. El partido cambie a estado "En juego", "Finalizado" o “Cancelado”.
            case "PARTIDO_ARMADO","PARTIDO_CONFIRMADO","PARTIDO_CANCELADO", "PARTIDO_FINALIZADO", "PARTIDO_EN_JUEGO" -> {
                partido.getJugadores().forEach(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getNombreUsuario(), null, usuario.getEmail());
                    Notificacion notificacion = new Notificacion(usuarioDTO, "El partido " + partido.getId() + " cambio de estado a " + partido.getEstado());
                    enviar(notificacion);
                });
            }
        }
    }

    /**
     * Obtiene la estrategia de notificación actual.
     * 
     * @return La estrategia de notificación actual o null si no se ha configurado ninguna
     */
    @Override
    public EstrategiaDeNotificacion getEstrategiaDeNotificacion() {
        return estrategiaDeNotificacion;
    }
    

}
