DO '
DECLARE
  newVersion     BIGINT := 7;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    alter table if exists ddrdailyreport add column if not exists reportDataHashCode BIGINT NOT NULL default 0;
    alter table if exists mludailyreport add column if not exists reportDataHashCode BIGINT NOT NULL default 0;
    alter table if exists muddailyreport add column if not exists reportDataHashCode BIGINT NOT NULL default 0;
    alter table if exists workoverdailyreport add column if not exists reportDataHashCode BIGINT NOT NULL default 0;

    private long reportDataHashCode;

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

