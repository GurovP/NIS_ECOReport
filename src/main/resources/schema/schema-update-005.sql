DO '
DECLARE
  newVersion     BIGINT := 5;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE IF NOT EXISTS workoverdailyreport
      (
          id BIGINT NOT NULL constraint workoverdailyreport_pkey primary key,
          reportUid VARCHAR(64),
          reportDate BIGINT,
          reportNumber TEXT,
          documentCode TEXT,
          comment TEXT,
          approvedDSV BOOLEAN NOT NULL,
          lastApproveDSVDate BIGINT,
          lastApproveDSVUser TEXT,
          approvedRTOC BOOLEAN NOT NULL,
          lastApproveRTOCDate BIGINT,
          lastApproveRTOCUser TEXT,
          wellNumGovt TEXT,
          wellNameLegal TEXT,
          wellField TEXT,
          wellCluster TEXT,
          wellCustomer TEXT,
          rigName TEXT,
          crewName TEXT,
          projectName TEXT,
          projectPhase INTEGER NOT NULL,
          workoverStartDate BIGINT,
          workoverEndDate BIGINT
      );

    CREATE TABLE IF NOT EXISTS workoverdailyreportdata
      (
          id BIGINT NOT NULL constraint workoverdailyreportdata_pkey primary key,
          dataType INTEGER NOT NULL,
          content JSONB NULL,
          report_id BIGINT NOT NULL
              constraint workoverdailyreportdata_workoverdailyreport_fkey
                  references workoverdailyreport
      );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

