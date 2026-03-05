
package eco.nis.models.entities.ddr;


public enum DDRDailyReportDataType {

    DatumGeneral (0),
    DatumProjectInfo (1),
    DatumDrillingInfo (2),
    DatumPlan (3),
    DatumReal (4),
    DatumWeight (5),
    DatumDrillingAssembly (6),
    DatumBitRecord (7),
    DatumBOPEquipment (8),
    DatumPlanFactInfo (9),
    DatumPlanFactInfoCasingFirst (10),
    DatumPlanFactInfoCasingSecond (11),
    DatumPlanFactInfoCasingInfo (12),
    DatumPlanFactInfoLinerInfo (13),
    DatumMudPumpRecord (14),
    DatumReducedFlowRateData (15),
    DatumMudRecord (16),
    DatumHydraulic (17),
    DatumDeviationRecorder (18),
    DatumTest (19),
    DatumDurationFirst (20),
    DatumDetailsOfOperations (21),
    DatumLastActivities (22),
    DatumPlanedActivities (23),
    DatumStocks (24),
    DatumDurationSecond (25),
    DatumTotalActivities (26),
    DatumLastHseTrainings (27),
    TimeDistributionDatum(28),
    NPT(30),
    DrillingAssemblyDatum(31),
    BitRecordsDatum(32),
    DatumChartGeneral(33),
    DatumDetailsOfOperationsSecond (34),
    DatumKillSheetGeneral(55),
    DatumKillSheetCirculation(56),
    DatumKillSheetLeakOff(57),
    DatumKillSheetPump(58),
    DatumKillSheetWell(59),
    DatumKillSheetWellDrillstring(60),
    DatumKillSheetWellCasing(61),
    DatumKillSheetWellAnnulus(62),
    DatumKillSheetSurfaceCycle(63),
    DatumKillSheetKick(64),
    DatumKillSheetStartCirculation(65),
    DatumKillSheetStrokesSteps(66),
    DatumKillSheetWeightingMaterial(67),
    DatumDrillstring(68),
    DatumCasing(69),
    DatumKillSheetWeightingMaterialLib(70),
    DatumKillSheetDrillstringDrillPipeLib(71),
    DatumKillSheetDrillstringDrillCollarLib(72),
    DatumKillSheetDrillstringHWDPLib(73),
    DatumKillSheetCasingLib(74),
    DatumKillSheetDatumPumpStks(75),
    DatumDrillstringSchemeTubular(76),
    DatumBHA(77),
    DatumKillSheetDatumMudData(78),
    DatumNPTInfo (79);

    private int code;

    DDRDailyReportDataType(int code) {

        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public static DDRDailyReportDataType fromCode(int code) {

        for(DDRDailyReportDataType cs: values())
            if(cs.code == code)
                return cs;

        return DDRDailyReportDataType.DatumGeneral;
    }
}
