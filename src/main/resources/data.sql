INSERT INTO PROJECT(name) VALUES ('Demo');

INSERT INTO TASK(project_id, title, assignee, status, due_date)
VALUES (1, 'Task A', 'mark', 'IN_PROGRESS', CURRENT_TIMESTAMP);

INSERT INTO TASK(project_id, title, assignee, status, due_date)
VALUES (1, 'Task B', 'sarah', 'TODO', DATEADD('DAY', 1, CURRENT_TIMESTAMP));
