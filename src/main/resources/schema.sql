-- ===================================================================================
-- SCHEMA: Defines the database structure for User, Project, Task, and Subprojects.
-- ===================================================================================

-- Create Users table
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each user
                       email VARCHAR(255) NOT NULL UNIQUE,    -- User email, must be unique
                       name VARCHAR(255) NOT NULL,            -- User's name
                       password VARCHAR(255) NOT NULL,        -- Encrypted password
                       role VARCHAR(50) NOT NULL              -- Role (e.g., ADMIN, USER)
);

-- Create Projects table
CREATE TABLE projects (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each project
                          name VARCHAR(255) NOT NULL,            -- Name of the project
                          description TEXT,                      -- Description of the project
                          creation_date DATE NOT NULL,           -- Date the project was created
                          start_date DATE,                       -- Optional start date
                          end_date DATE,                         -- Optional end date
                          creator_id BIGINT,                     -- Foreign key linking to the user who created the project
                          is_subproject BOOLEAN DEFAULT FALSE,   -- Indicates if this project is a subproject
                          FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create Tasks table
CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each task
                       name VARCHAR(255) NOT NULL,            -- Name of the task
                       description TEXT,                      -- Description of the task
                       creation_date DATE NOT NULL,           -- Date the task was created
                       estimate INT,                          -- Estimated duration in hours
                       start_date DATE,                       -- Optional start date
                       end_date DATE,                         -- Optional end date
                       status VARCHAR(50),                    -- Status (e.g., IN_PROGRESS, COMPLETED)
                       is_subtask BOOLEAN DEFAULT FALSE,      -- Indicates if this task is a subtask
                       project_id BIGINT,                     -- Foreign key linking to the project this task belongs to
                       FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Create Subprojects table
CREATE TABLE subprojects (
                             project_id BIGINT NOT NULL,            -- Parent project ID
                             subproject_id BIGINT NOT NULL,         -- Subproject ID
                             PRIMARY KEY (project_id, subproject_id),  -- Composite key for the relationship
                             FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                             FOREIGN KEY (subproject_id) REFERENCES projects(id) ON DELETE CASCADE
);
