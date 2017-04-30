ALTER TABLE player_notes rename COLUMN notes TO summary;
ALTER TABLE player_notes rename COLUMN grade TO overall_grade;
ALTER TABLE player_notes ADD COLUMN strengths text;
ALTER TABLE player_notes ADD COLUMN weaknesses text;
ALTER TABLE player_notes ADD COLUMN likeness integer;
ALTER TABLE player_notes ADD COLUMN projected_round integer;