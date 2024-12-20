    -- Create Clients table
    CREATE TABLE clients (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        contact_name VARCHAR(255),
        address VARCHAR(255),
        city VARCHAR(100),
        zip VARCHAR(20),
        phone VARCHAR(20),
        email VARCHAR(255)
    );

    -- Create Users table
    CREATE TABLE users (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL UNIQUE,
        name VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        role VARCHAR(50) NOT NULL
    );

    -- Create Projects table
    CREATE TABLE projects (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT,
        creation_date DATE NOT NULL,
        start_date DATE,
        end_date DATE,
        creator_id BIGINT,
        client_id BIGINT,
        is_subproject BOOLEAN DEFAULT FALSE,
        FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE SET NULL,
        FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL
    );

    -- Create Tasks table
    CREATE TABLE tasks (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT,
        creation_date DATE NOT NULL,
        estimate INT,
        start_date DATE,
        end_date DATE,
        status VARCHAR(50),
        is_subtask BOOLEAN DEFAULT FALSE,
        project_id BIGINT,
        FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
    );

    -- Create Subprojects table
    CREATE TABLE subprojects (
        project_id BIGINT NOT NULL,
        subproject_id BIGINT NOT NULL,
        PRIMARY KEY (project_id, subproject_id),
        FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
        FOREIGN KEY (subproject_id) REFERENCES projects(id) ON DELETE CASCADE
    );
    -- Create Competences table
    CREATE TABLE competences (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS tools (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        tool_value DECIMAL(10, 2) NOT NULL
        );

    -- Create Task_Competences relation table
    CREATE TABLE task_competences (
        task_id BIGINT NOT NULL,
        competence_id BIGINT NOT NULL,
        PRIMARY KEY (task_id, competence_id),
        FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
        FOREIGN KEY (competence_id) REFERENCES competences(id) ON DELETE CASCADE
    );

    -- Create Task_Tools relation table
    CREATE TABLE task_tools (
        task_id BIGINT NOT NULL,
        tool_id BIGINT NOT NULL,
        PRIMARY KEY (task_id, tool_id),
        FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
        FOREIGN KEY (tool_id) REFERENCES tools(id) ON DELETE CASCADE
    );
