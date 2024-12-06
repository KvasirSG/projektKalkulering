-- Insert test clients
INSERT INTO clients (name, contact_name, address, city, zip, phone, email) VALUES
    ('Acme Corporation', 'John Doe', '123 Main St', 'Metropolis', '12345', '555-1234', 'contact@acme.com'),
    ('Tech Solutions', 'Jane Smith', '456 Elm St', 'Gotham', '54321', '555-5678', 'info@techsolutions.com');

-- Insert test users
INSERT INTO users (email, name, password, role) VALUES
    ('admin@example.com', 'Admin User', 'adminpassword', 'ADMIN'),
    ('user@example.com', 'Regular User', 'userpassword', 'USER');

-- Insert test projects
INSERT INTO projects (name, description, creation_date, start_date, end_date, creator_id, client_id, is_subproject) VALUES
    ('Project A', 'Description of Project A', CURRENT_DATE, CURRENT_DATE, NULL, 1, 1, FALSE),
    ('Project B', 'Description of Project B', CURRENT_DATE, CURRENT_DATE, NULL, 2, 2, TRUE);

-- Insert test tasks
INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_subtask, project_id) VALUES
    ('Task 1', 'Description of Task 1', CURRENT_DATE, 5, CURRENT_DATE, NULL, 'IN_PROGRESS', FALSE, 1),
    ('Task 2', 'Description of Task 2', CURRENT_DATE, 3, CURRENT_DATE, NULL, 'COMPLETED', TRUE, 1);

-- Insert test subprojects
INSERT INTO subprojects (project_id, subproject_id) VALUES
    (1, 2);
