package it.uniroma3.Odiazon.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class ShutdownConfig {

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @EventListener(ContextClosedEvent.class)
    public void onContextClosed() {
        
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;

        if (os.contains("win")) {
            System.out.println("Rilevato Windows, eseguo lo script PowerShell...");
            processBuilder = new ProcessBuilder("powershell.exe", "-ExecutionPolicy", "Bypass", "-File", "shutdown-script.ps1");
        } else {
            System.out.println("Rilevato macOS/Linux, eseguo lo script Shell...");
            processBuilder = new ProcessBuilder("bash", "shutdown-script.sh");
        }

        try {
            // Aggiungo la password del DB all'ambiente del processo per pg_dump
            if (databasePassword != null && !databasePassword.isEmpty()) {
                processBuilder.environment().put("PGPASSWORD", databasePassword);
            }

            Process process = processBuilder.start();
            
            // Aspetta un massimo di 30 secondi per il completamento del comando
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);

            if (finished) {
                System.out.println("Comando di shutdown eseguito con codice di uscita: " + process.exitValue());
            } else {
                System.out.println("Il comando di shutdown non è terminato entro 30 secondi, forzo l'interruzione.");
                process.destroyForcibly();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Errore durante l'esecuzione del comando di shutdown: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
