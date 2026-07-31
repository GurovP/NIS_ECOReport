package eco.nis.services.dvsd;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.dvsd.ECOReportingDvsDApiService;
import eco.nis.repositories.DvsDTableRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ECODvsDTableService {

    private final ECODvsDTableWorker worker;
    private final ScheduledExecutorService scheduledExecutorService;

    public ECODvsDTableService(PlatformTransactionManager transactionManager,
                               ECOReportingDvsDApiService dvsDApiService,
                               DvsDTableRepository dvsDTableRepository,
                               ECOReportingSyncLogRepository ecoReportingSyncLogRepository) {

        this.worker = new ECODvsDTableWorker(
                transactionManager,
                dvsDApiService,
                dvsDTableRepository,
                ecoReportingSyncLogRepository,
                NIS_ECOReportApplication.logger);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        startECODvsDTablePull();
    }

    public void startECODvsDTablePull() {

        NIS_ECOReportApplication.logger.info("ECODvsDTableService started.");
        scheduledExecutorService.scheduleWithFixedDelay(worker::pullECOReport, 9, 10, TimeUnit.MINUTES);
    }
}
