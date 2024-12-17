-- Insert data into Clients table
INSERT INTO clients (name, contact_name, address, city, zip, phone, email) VALUES
                                                                               ('Tech Solutions', 'John Doe', '123 Main St', 'New York', '10001', '555-1234', 'johndoe@techsolutions.com'),
                                                                               ('Creative Studio', 'Jane Smith', '456 Elm St', 'Los Angeles', '90002', '555-5678', 'janesmith@creativestudio.com'),
                                                                               ('Business Corp', 'Mike Johnson', '789 Pine St', 'Chicago', '60603', '555-9012', 'mikejohnson@businesscorp.com');

-- Insert data into Users table
INSERT INTO users (email, name, password, role) VALUES
                                                    ('admin@company.com', 'Admin User', 'hashedpassword1', 'Admin'),
                                                    ('project.manager@company.com', 'Project Manager', 'hashedpassword2', 'Manager'),
                                                    ('developer@company.com', 'Developer User', 'hashedpassword3', 'Developer');

-- Insert data into Projects table
INSERT INTO projects (name, description, creation_date, start_date, end_date, creator_id, client_id, is_subproject) VALUES
                                                                                                                        ('Website Redesign', 'Redesign the client website with a modern layout.', '2024-06-01', '2024-06-10', '2024-09-10', 2, 1, FALSE),
                                                                                                                        ('Marketing Campaign', 'Digital marketing campaign for new product.', '2024-05-15', '2024-06-01', '2024-07-01', 2, 2, FALSE),
                                                                                                                        ('Mobile App Development', 'Develop a cross-platform mobile app.', '2024-06-05', '2024-07-01', '2024-12-01', 3, 3, FALSE);

-- Insert data into Tasks table
INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_subtask, project_id) VALUES
                                                                                                                         ('Design Wireframes', 'Create initial wireframe designs for the project.', '2024-06-01', 10, '2024-06-10', '2024-06-20', 'In Progress', FALSE, 1),
                                                                                                                         ('SEO Optimization', 'Optimize content for search engines.', '2024-05-16', 5, '2024-06-01', '2024-06-10', 'Not Started', FALSE, 2),
                                                                                                                         ('App Backend Development', 'Set up backend APIs and database for the app.', '2024-06-06', 15, '2024-07-01', '2024-08-01', 'Planned', FALSE, 3);

-- Insert data into Competences table
INSERT INTO competences (name) VALUES
                                   ('UI/UX Design'),
                                   ('SEO Specialist'),
                                   ('Backend Development'),
                                   ('Project Management');

-- Insert data into Tools table
INSERT INTO tools (name) VALUES
                             ('Figma'),
                             ('Google Analytics'),
                             ('Node.js'),
                             ('JIRA');

-- Insert data into Task_Competences table
INSERT INTO task_competences (task_id, competence_id) VALUES
                                                          (1, 1), -- Design Wireframes -> UI/UX Design
                                                          (2, 2), -- SEO Optimization -> SEO Specialist
                                                          (3, 3); -- App Backend Development -> Backend Development

-- Insert data into Task_Tools table
INSERT INTO task_tools (task_id, tool_id) VALUES
                                              (1, 1), -- Design Wireframes -> Figma
                                              (2, 2), -- SEO Optimization -> Google Analytics
                                              (3, 3); -- App Backend Development -> Node.js

-- Insert data into Subprojects table
INSERT INTO subprojects (project_id, subproject_id) VALUES
                                                        (1, 2), -- Website Redesign has a subproject Marketing Campaign
                                                        (3, 1); -- Mobile App Development depends on Website Redesign
