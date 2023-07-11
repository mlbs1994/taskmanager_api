ALTER TABLE task
ADD COLUMN department bigint,
ADD CONSTRAINT fk_department
FOREIGN KEY (department)
REFERENCES department(id)

