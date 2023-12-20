package ar.edu.ubp.das.mss.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ar.edu.ubp.das.mss.repositories.MSSRepository;

@Service
public class ScheduledTasks {
    @Autowired
    private MSSRepository repo;
    
    //A realizar cada 12 horas
    @Scheduled(cron = "0 0 */12 * * *")
    public void finalizarFederacionesPendientes(){
        try {
            repo.finalizarFederacionesPendientes();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    //A realizar a las 12:00:00 del 5 día de cada mes
    @Scheduled(cron = "0 0 12 5 * *")
    public void enviarFacturas(){
        try {
            repo.enviarFacturas();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //A realizar a las 12:00:00 del 5 día de cada mes
    @Scheduled(cron = "0 21 12 * * ?")
    public void enviarEstadisticas(){
        try {
            System.out.println("Enviando estadísticas");
            repo.enviarEstadisticas();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //A realizar cada 12 horas
    @Scheduled(cron = "0 0 */12 * * *")
    public void actualizarCatalogo(){
        try {
            repo.actualizarCatalogo();
            System.out.println("actualizando catálogo");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //A realizar cada 12 horas
    @Scheduled(cron = "0 0 */12 * * *")
    public void actualizarPublicidades(){
        try {
            repo.actualizarPublicidades();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
