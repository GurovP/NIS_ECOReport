
package eco.nis.models.entities.mud;


public enum MudDailyReportDataType {

    GeneralInfo (0),
    BHA(1),
    Casing(2),
    OpenHole(3),
    CirculationSystem(4),
    CirculationData(5),
    MaterialConsumption(6),
    Parameters(7),
    Cleaners(8),
    NotesLast24Hours(9),
    NotesFluid(10),
    Volume(11),
    Losses(12),
    Trajectory(13),
    Inventory(14),
    DisplacementFluid(15),
    DisplacementFluidWellbore(16),
    GeneralInfoLastPage(17),
    EfficiencyOfSolids(18),
    ScreensOnVibrators(19),
    ScreenStatus(20),
    OperationOfSolids(21),
    CleanersNote(22),
    PlanNext24Hours(23),
    TechnicalWaterAnalysis(24),
    LithologicalProfileDatum(25),
    Operation24Hours(26),
    Hydraulics(27),
    TotalQuantity(28);

    private int code;

    MudDailyReportDataType(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public static MudDailyReportDataType fromCode(int code) {

        for(MudDailyReportDataType cs: values())
            if(cs.code == code)
                return cs;

        return MudDailyReportDataType.GeneralInfo;
    }
}
