CREATE TABLE employee_attendance
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255),
    employee_id        INT,
    workday_date       DATE,
    check_in_time      TIME,
    check_out_time     TIME,
    ext                VARCHAR(255),
    is_full_attendance bit(1) DEFAULT 0, INDEX idx_employee_id (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
