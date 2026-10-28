package eco.nis.services.mlu;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.mlu.ECOReportingMLUApiService;
import eco.nis.repositories.MLUDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ECOReportMLUService {

    private final ECOReportMLUWorker worker;
    private final ScheduledExecutorService scheduledExecutorService;

    public ECOReportMLUService(PlatformTransactionManager transactionManager,
                               ECOReportingMLUApiService mluApiService,
                               MLUDailyReportRepository mluDailyReportRepository,
                               ECOReportingSyncLogRepository ecoReportingSyncLogRepository) {

        this.worker = new ECOReportMLUWorker(
                transactionManager,
                mluApiService,
                mluDailyReportRepository,
                ecoReportingSyncLogRepository,
                NIS_ECOReportApplication.logger);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        startECOReportMLUPull();
    }

    public void startECOReportMLUPull() {

        NIS_ECOReportApplication.logger.info("ECOReportMLUService started.");
        scheduledExecutorService.scheduleWithFixedDelay(worker::pullECOReport, 9, 30, TimeUnit.MINUTES);
    }
}
