-- Create created_at column if it doesn't exist (MySQL 8+ supports IF NOT EXISTS)
ALTER TABLE users
  ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
