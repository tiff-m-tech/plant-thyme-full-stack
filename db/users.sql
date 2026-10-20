-- Seed data for the demo user account.
-- The password_hash is a BCrypt hash (the real password is in the README demo credentials).
-- Run AFTER plants.sql and BEFORE collection_plants.sql.
-- 'users' table

USE plant_thyme;

INSERT INTO users (id, username, email, password_hash, created_at)
VALUES
    (1, 'tiffany', 'tiffany@example.com', '$2a$10$7WBjUyGe77tNWkDfDDNZxun0x4yefWWGqsBS5RVXaUY./dadiGF1C', NOW());