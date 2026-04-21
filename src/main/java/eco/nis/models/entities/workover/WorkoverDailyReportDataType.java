
package eco.nis.models.entities.workover;


public enum WorkoverDailyReportDataType {

    GeneralInfo (0),
    WellInfo (1),
    MorningPressureMeasurements(2),
    ActivityFirst (3),
    ActivitySecond (4),
    Bha (5),
    WorkingFluid (6),
    Tubulars (7),
    Tools (8),
    Drills (9),
    Analysis (10),
    FieldPersonnelFirst (11),
    FieldPersonnelSecond (12),
    ScheduledOperations (13);

    private int code;

    WorkoverDailyReportDataType(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public static WorkoverDailyReportDataType fromCode(int code) {

        for(WorkoverDailyReportDataType cs: values())
            if(cs.code == code)
                return cs;

        return WorkoverDailyReportDataType.GeneralInfo;
    }
}
