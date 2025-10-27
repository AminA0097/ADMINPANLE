CREATE TABLE user_action (
    fld_id SERIAL PRIMARY KEY,
    fld_user_id BIGINT NOT NULL,
    fld_entity_name VARCHAR(255) NOT NULL,
    fld_action_id BIGINT NOT NULL,
    fld_status SMALLINT DEFAULT 1,      -- 1 = active, 0 = inactive
fld_deleted SMALLINT DEFAULT 0,     -- 0 = not deleted, 1 = deleted
fld_created_at TIMESTAMP DEFAULT NOW(),
    fld_updated_at TIMESTAMP DEFAULT NOW()
);

