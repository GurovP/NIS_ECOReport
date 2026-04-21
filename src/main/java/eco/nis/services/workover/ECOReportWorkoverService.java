package eco.nis.services.workover;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.workover.ECOReportingWorkoverApiService;
import eco.nis.repositories.WorkoverDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ECOReportWorkoverService {

    private final ECOReportWorkoverWorker worker;
    private final ScheduledExecutorService scheduledExecutorService;

    public ECOReportWorkoverService(PlatformTransactionManager transactionManager,
                                    ECOReportingWorkoverApiService workoverApiService,
                                    WorkoverDailyReportRepository workoverDailyReportRepository,
                                    ECOReportingSyncLogRepository ecoReportingSyncLogRepository) {

        this.worker = new ECOReportWorkoverWorker(
                transactionManager,
                workoverApiService,
                workoverDailyReportRepository,
                ecoReportingSyncLogRepository,
                NIS_ECOReportApplication.logger);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        startECOReportWorkoverPull();
    }

    public void startECOReportWorkoverPull() {

        NIS_ECOReportApplication.logger.info("ECOReportWorkoverService started.");
        scheduledExecutorService.scheduleWithFixedDelay(worker::pullECOReport, 7, 10, TimeUnit.MINUTES);
    }
}
