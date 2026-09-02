-- Plant Thyme: seed data for the master plant database
-- 34 plants for the `plant` table
-- Run this once in MySQL Workbench against the plant_thyme schema.

USE plant_thyme;

INSERT INTO plant (name, image_path, light, water, fertilize) VALUES
    ('Angel Wing Begonia', 'angel-wing-begonia.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'every 2 to 4 weeks in spring & summer'),
    ('Batik Syngonium', 'batik-syngonium.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Brasil Philodendron', 'brasil-philodendron.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Caperata Peperomia', 'caperata-peperomia.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Gold Dust Croton', 'gold-dust-croton.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Golden Ivy', 'golden-ivy.webp', 'medium to bright indirect light | 4 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Golden Pothos', 'golden-pothos.webp', 'medium to bright indirect light | 4 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Green Ripple Peperomia', 'green-ripple-peperomia.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Heartleaf Philodendron', 'heartleaf-philodendron.webp', 'medium to bright indirect light | 4 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Manjula Pothos', 'manjula-pothos.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Marble Queen Pothos', 'marble-queen-pothos.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Micans Philodendron', 'micans-philodendron.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Money Tree', 'money-tree.webp', 'bright indirect light | 6 to 8 hours', 'when the top two to three inches of soil are dry', 'monthly in spring & summer'),
    ('Neon Pothos', 'neon-pothos.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Neon Queen Pothos', 'neon-queen-pothos.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Non Variegated Monstera', 'non-variegated-monstera.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Peace Lily', 'peace-lily.webp', 'medium to bright indirect light | 4 to 8 hours', 'when the top inch of soil is dry', 'every 4 to 6 weeks in spring & summer'),
    ('Pearls and Jade Pothos', 'pearls-and-jade-pothos.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Petra Croton', 'petra-croton.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Piccolo Banda Peperomia', 'piccolo-banda-peperomia.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Pink Syngonium', 'pink-syngonium.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Pittsburgh Ivy', 'pittsburgh-ivy.webp', 'medium to bright indirect light | 4 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Purple African Violet', 'purple-african-violet.webp', 'bright indirect light | 8 to 12 hours', 'when the top inch of soil feels slightly dry', 'every 2 to 4 weeks during active growth'),
    ('Red Kiss Rex Begonia', 'red-kiss-rex-begonia.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Ring of Fire Philodendron', 'ring-of-fire-philodendron.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Rosso Peperomia', 'rosso-peperomia.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Snake Plant', 'snake-plant.webp', 'low to bright indirect light | 4 to 8 hours', 'when the soil is completely dry', 'every 6 to 8 weeks in spring & summer'),
    ('Spider Plant', 'spider-plant.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('String of Hearts', 'string-of-hearts.webp', 'bright indirect light | 6 to 8 hours', 'when most of the soil is dry', 'monthly in spring & summer'),
    ('String of Pearls', 'string-of-pearls.webp', 'bright indirect light | 6 to 8 hours', 'when the soil is almost completely dry', 'monthly in spring & summer'),
    ('Thai Constellation Monstera', 'thai-constellation-monstera.webp', 'bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('Variegated Syngonium', 'variegated-syngonium.webp', 'bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer'),
    ('Watermelon Peperomia', 'watermelon-peperomia.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top two inches of soil are dry', 'monthly in spring & summer'),
    ('White Butterfly Syngonium', 'white-butterfly-syngonium.webp', 'medium to bright indirect light | 6 to 8 hours', 'when the top inch of soil is dry', 'monthly in spring & summer');
