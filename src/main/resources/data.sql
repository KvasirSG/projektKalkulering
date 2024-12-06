-- ===================================================================================
-- DATA: Populates the database with updated test data for users, projects, tasks, and subprojects.
-- ===================================================================================

-- Insert test users
INSERT INTO users (email, name, password, role) VALUES
    ('admin@example.com', 'Admin User', 'adminpassword', 'ADMIN'),
    ('user@example.com', 'Regular User', 'userpassword', 'USER'),
    ('manager@example.com', 'Project Manager', 'managerpassword', 'USER'),
    ('developer@example.com', 'Developer', 'developerpassword', 'USER');

-- Insert test clients
INSERT INTO clients (name, contact_name, address, city, zip, phone, email) VALUES
    ('Acme Corporation', 'John Doe', '123 Main St', 'Metropolis', '12345', '555-1234', 'contact@acme.com'),
    ('Tech Solutions', 'Jane Smith', '456 Elm St', 'Gotham', '54321', '555-5678', 'info@techsolutions.com');

-- Insert test projects
INSERT INTO projects (name, description, creation_date, start_date, end_date, creator_id, client_id, is_subproject) VALUES
    ('Project A', 'Description of Project A', CURRENT_DATE, CURRENT_DATE, NULL, 1, 1, FALSE),
    ('Project B', 'Description of Project B', CURRENT_DATE, CURRENT_DATE, NULL, 2, 2, TRUE),
    ('Project C', 'Description of Project C', CURRENT_DATE, CURRENT_DATE, NULL, 3, 1, FALSE),
    ('Project D', 'Description of Project D', CURRENT_DATE, CURRENT_DATE, NULL, 4, NULL, TRUE);

-- Insert test tasks
INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_subtask, project_id) VALUES
    ('Task 1', 'Description of Task 1', CURRENT_DATE, 5, CURRENT_DATE, NULL, 'IN_PROGRESS', FALSE, 1),
    ('Task 2', 'Description of Task 2', CURRENT_DATE, 3, CURRENT_DATE, NULL, 'COMPLETED', TRUE, 1),
    ('Task 3', 'Description of Task 3', CURRENT_DATE, 8, CURRENT_DATE, NULL, 'NOT_STARTED', FALSE, 2),
    ('Task 4', 'Description of Task 4', CURRENT_DATE, 2, CURRENT_DATE, NULL, 'IN_PROGRESS', TRUE, 3),
    ('Task 5', 'Description of Task 5', CURRENT_DATE, 10, CURRENT_DATE, NULL, 'COMPLETED', FALSE, 4);

-- Insert test subprojects (relationships between projects)
INSERT INTO subprojects (project_id, subproject_id) VALUES
    (1, 2),  -- Project A has Project B as a subproject
    (3, 4);  -- Project C has Project D as a subproject
