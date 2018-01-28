alter table player add COLUMN "source" TEXT NULL;
update player set source = 'CBS';