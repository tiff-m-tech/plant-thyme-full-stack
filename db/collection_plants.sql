-- Plant Thyme: seed data for the user's current collection
-- Based on the original currentCollection.js mock data.
-- Run AFTER plants.sql so the referenced plant IDs already exist.

USE plant_thyme;

INSERT INTO collection_plant
(plant_id, purchase_date, purchase_store, cost, nickname, location, notes)
VALUES
    (6,  '2024-03-15', 'Home Depot',      9.99,  NULL,       'Living Room', 'Seems to be a magnet for spider mites.'),
    (7,  '2024-06-12', 'Home Depot',     12.99,  'Goldie',   'Kitchen',     'One of the first plants I ever bought!'),
    (10, '2024-08-24', 'Local Nursery',  16.99,  NULL,       'Bedroom',     NULL),
    (11, '2024-04-07', 'Lowe''s',        11.99,  'Marble',   'Office',      'Marble queen and snow queen are the same thing - people say snow queen when it''s highly variegated.'),
    (14, '2025-01-18', 'Trader Joe''s',   8.99,  NULL,       'Bathroom',    NULL),
    (15, '2025-03-02', 'Local Nursery',  18.99,  'Queenie',  'Bedroom',     'Needs less sun then it''s darker green cousins. I had this at the same north facing window as my Golden and it bleached the leaves!'),
    (18, '2024-11-09', 'Walmart',        10.98,  NULL,       'Living Room', NULL),
    (5,  '2024-05-21', 'Home Depot',     14.99,  'Dusty',    'Dining Room', NULL),
    (19, '2025-02-14', 'Lowe''s',        15.99,  NULL,       'Living Room', 'Needs to be watered every few days.'),
    (17, '2024-09-30', 'Trader Joe''s',  10.99,  'Lily',     'Bedroom',     'Doesn''t bloom when it''s not getting enough sun.'),
    (28, '2024-02-17', 'Local Nursery',   7.99,  NULL,       'Kitchen',     'When you have one spider plant you eventually have 100''s! I didn''t realise there would be so many baby plants!'),
    (3,  '2024-07-10', 'Home Depot',     12.99,  NULL,       'Bathroom',    'Currently more than 10 feet long - going up the bathroom wall and across the ceiling now. 4 years old!'),
    (16, '2025-04-11', 'Lowe''s',        24.99,  'Monty',    'Living Room', NULL),
    (23, '2024-12-03', 'Trader Joe''s',   5.99,  'Violet',   'Kitchen',     'Wont bloom unless it gets plenty of sun.'),
    (29, '2025-05-06', 'Local Nursery',  14.99,  NULL,       'Bedroom',     NULL),
    (24, '2025-06-01', 'Home Depot',     13.99,  'Ruby',     'Office',      NULL),
