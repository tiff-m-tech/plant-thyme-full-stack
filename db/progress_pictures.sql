-- Plant Thyme: seed data for existing progress pictures
-- Run AFTER collection_plants.sql.
-- This assumes the future ProgressPicture entity/table uses:
-- id, collection_plant_id, image_path, date, update_type, notes
--
-- The original mock data had three progress pictures for CollectionPlant 112
-- (Brasil Philodendron). Update types and notes below are new sample data
-- and can be changed later.

USE plant_thyme;

INSERT INTO progress_picture
(collection_plant_id, image_path, picture_date, update_type, notes)
VALUES
    (12, 'brasil-progress-picture-1.webp', '2025-07-12', 'Growth',
     'Progress photo showing the plant continuing to grow and trail.'),
    (12, 'brasil-progress-picture-2.webp', '2026-01-06', 'Growth',
     'New progress photo added to compare growth with the previous update.'),
    (12, 'brasil-progress-picture-3.webp', '2026-04-28', 'Growth',
     'Latest progress photo showing how much the plant has changed over time.');