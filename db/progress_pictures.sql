-- Seed data for existing progress pictures.
-- Run AFTER collection_plants.sql so the referenced collection_plant IDs already exist.
-- Based off original mock data that had three progress pictures for CollectionPlant 12 (Brasil Philodendron).
-- demo-images folder in root has sample progress picture images you can upload for the Golden Pothos.

USE plant_thyme;

INSERT INTO progress_picture
(collection_plant_id, image_path, picture_date, update_type, notes)
VALUES
    (12, 'brasil-progress-picture-1.webp', '2025-07-12', 'Growth',
     'Pic from when I originally bought it.'),
    (12, 'brasil-progress-picture-2.webp', '2026-01-06', 'Growth',
     'New progress photo added to compare growth with the previous picture.'),
    (12, 'brasil-progress-picture-3.webp', '2026-04-28', 'Growth',
     'Latest progress photo showing how much the plant has changed over time.');