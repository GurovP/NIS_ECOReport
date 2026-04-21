package eco.nis.services.ddr;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ddr.ECOReportingDDRApiService;
import eco.nis.repositories.DDRDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ECOReportDDRService {

    private final ECOReportDDRWorker worker;
    private final ScheduledExecutorService scheduledExecutorService;

    public ECOReportDDRService(PlatformTransactionManager transactionManager,
                               ECOReportingDDRApiService ddrApiService,
                               DDRDailyReportRepository ddrDailyReportRepository,
                               ECOReportingSyncLogRepository ecoReportingSyncLogRepository) {

        this.worker = new ECOReportDDRWorker(
                transactionManager,
                ddrApiService,
                ddrDailyReportRepository,
                ecoReportingSyncLogRepository,
                NIS_ECOReportApplication.logger);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        startECOReportDDRPull();
    }

    public void startECOReportDDRPull() {

        NIS_ECOReportApplication.logger.info("ECOReportDDRService started.");
        scheduledExecutorService.scheduleWithFixedDelay(worker::pullECOReport, 1, 10, TimeUnit.MINUTES);
    }
}
