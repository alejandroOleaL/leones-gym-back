INSERT INTO periodos (id, periodo, nombre) VALUES(1, 7, 'Semana');
INSERT INTO periodos (id, periodo, nombre) VALUES(2, 15, 'Quincenal');
INSERT INTO periodos (id, periodo, nombre) VALUES(3, 30, 'Mes');
INSERT INTO periodos (id, periodo, nombre) VALUES(4, 90, 'Trimestre');
INSERT INTO periodos (id, periodo, nombre) VALUES(5, 180, 'Semestre');
INSERT INTO periodos (id, periodo, nombre) VALUES(6, 365, 'Anual');

INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(1, 'alex', 'locia', '2023-11-08', '7471', 'alex@mail.com', '', 1203, 7, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(2, 'bryan', 'lopez', '2023-09-22', '837636', 'bryan@mail.com', '', 1111, 15, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'wilianismo', '',  '2023-03-12', '64747', 'wiliam@mail.com', '', 8621, 30, false);

INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('supremo', 'kaiosama', 'sokaio@mail.com', 'user', '$2a$10$lm8H0.J3pi1FpM3s3CUpe.Ym0FnlwHFclzGIGoWItLCN1vIFWEMmm', 1);
INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('andres', 'gordo', 'angor@mail.com', 'admin', '$2a$10$g2//5daw/XMhgjPbkryr7OL5h05zauUHrNhBL8ey6V0NNQHV/3dGm', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 2);

