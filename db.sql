CREATE DATABASE crm;
USE crm;


CREATE TABLE role (
    role_id INT AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    description TEXT,
    
    PRIMARY KEY (role_id)
);


CREATE TABLE user (
    user_id INT AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address TEXT,
    phone_number VARCHAR(20),
    role_id INT,
    
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE user_tokens (
    user_id INT,
    auth_token VARCHAR(255) NOT NULL,
    expiration_time DATETIME NOT NULL,
    
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE project (
    project_id INT AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    leader_id INT,
    
    PRIMARY KEY (project_id),
    FOREIGN KEY (leader_id) REFERENCES user(user_id)
);

CREATE TABLE task_status (
	status_id INT AUTO_INCREMENT,
    status_name VARCHAR(50) NOT NULL,
    
    PRIMARY KEY (status_id)
)

CREATE TABLE task (
    task_id INT AUTO_INCREMENT,
    task_name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    assignee_id INT,
    project_id INT,
    status_id INT,
    
    PRIMARY KEY (task_id),
    FOREIGN KEY (assignee_id) REFERENCES user(user_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id),
    FOREIGN KEY (status_id) REFERENCES task_status(status_id)
);

-- Insert
INSERT INTO task_status (status_name) VALUES 
('Not Started'),
('In Progress'),
('Completed');

INSERT INTO role (role_name, description) VALUES 
('Admin', ('Quản trị hệ thống')),
('Leader', ('Quản lý dự án')),
('Member', ('Nhân viên'));

-- password: abcd1212
INSERT INTO user (email , password, full_name, address, phone_number, role_id) VALUES 
('abs@gmail.com', 'EK/FJQYH3jpAHmlc1Iql4A==:bVjWofUJDAfPjg4HhoHe6A==', 'howard roark', '123 tran quang khai', '012344567', 1),
('member@gmail.com', '8ANVQnPoy3eGQA6OEc8kNw==:poK/IaGfDctzCZR6leRP3A==', 'howard roark', '123 tran quang khai', '012344567', 3),
('leader@gmail.com', 'LdDPCKnpGhkvvOhhZntH5g==:6Y+rmdBeLXix3mWeu26ZJg==', 'howard roark', '123 tran quang khai', '012344567', 2);

INSERT INTO project (project_name , description, start_date, end_date, leader_id) VALUES 
("Phân tích dự án2", "Phân tích dự án2", "2024-11-01", "2024-11-20", 2),
("Thiết kế hệ thống	2", "Thiết kế hệ thống", "2024-10-21", "2024-12-31", 2);

INSERT INTO task (task_name , description, start_date, end_date, assignee_id, project_id, status_id) VALUES 
("Phân tích 2", "Phân tích dự án 2", "2024-11-01", "2024-11-20", 3, 1, 1),
("Thiết kế database2", "Thiết kế database 2221", "2024-11-01", "2024-11-20", 3, 1, 1);
