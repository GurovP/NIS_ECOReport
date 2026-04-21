
package eco.nis.models.entities.mlu;


public enum MLUDailyReportDataType {

    GeneralInfo (0),
    CompletedOperations (1),
    DrillingFluid (2),
    Drilling (3),
    Geology (4),
    Calcimetry (5),
    BackgroundGas (6),
    ConnectionGas (7),
    Fluorescence (8),
    Stratigraphy (9),
    Surveys (10),
    Losses (11),
    Notes (12),
    Specialist (13);

    private int code;

    MLUDailyReportDataType(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public static MLUDailyReportDataType fromCode(int code) {

        for(MLUDailyReportDataType cs: values())
            if(cs.code == code)
                return cs;

        return MLUDailyReportDataType.GeneralInfo;
    }
}
