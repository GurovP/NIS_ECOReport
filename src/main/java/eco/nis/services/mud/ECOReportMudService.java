package eco.nis.services.mud;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.mud.ECOReportingMudApiService;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.repositories.MudDailyReportRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ECOReportMudService {

    private final ECOReportMudWorker worker;
    private final ScheduledExecutorService scheduledExecutorService;

    public ECOReportMudService(PlatformTransactionManager transactionManager,
                               ECOReportingMudApiService mudApiService,
                               MudDailyReportRepository mudDailyReportRepository,
                               ECOReportingSyncLogRepository ecoReportingSyncLogRepository) {

        this.worker = new ECOReportMudWorker(
                transactionManager,
                mudApiService,
                mudDailyReportRepository,
                ecoReportingSyncLogRepository,
                NIS_ECOReportApplication.logger);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        startECOReportMUDPull();
    }

    public void startECOReportMUDPull() {

        NIS_ECOReportApplication.logger.info("ECOReportMudService started.");
        scheduledExecutorService.scheduleWithFixedDelay(worker::pullECOReport, 15, 30, TimeUnit.MINUTES);
    }
}
