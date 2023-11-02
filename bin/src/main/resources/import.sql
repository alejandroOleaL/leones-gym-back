INSERT INTO periodos (id, periodo) VALUES(1, 7);
INSERT INTO periodos (id, periodo) VALUES(2, 15);
INSERT INTO periodos (id, periodo) VALUES(3, 30);

INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(1, 'alex', 'locia', '2023-08-08', '7471', 'alex@mail.com', '', 1203, 7, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(2, 'bryan', 'lopez', '2023-09-22', '837636', 'bryan@mail.com', '', 1111, 15, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'wilianismo', '',  '2023-03-12', '64747', 'wiliam@mail.com', '', 8621, 30, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'andres', 'gracida', '2023-10-10', '7471', 'andres@mail.com', '', 0292, 7, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(2, 'andres', 'gallardo', '2023-04-09', '837636', 'gracida@mail.com', '', 5546, 15, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(1, 'axel', 'gracida', '2023-03-12', '64747', 'axel@mail.com', '', 0090, 30, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'axel', 'gallardo', '2023-05-10', '7471', 'gall@mail.com', '', 6473, 30, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'atzel', 'alvarez', '2023-04-09', '837636', 'alv@mail.com', '', 8263, 15, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(2, 'fernando', 'alvarez', '2023-03-12', '64747', 'fer@mail.com', '', 6478, 30, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'atzel', 'gallardo', '2023-05-10', '7471', 'atz@mail.com', '', 4758, 7, false);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(1, 'fernando', 'gallardo', '2023-09-09', '837636', 'ga@mail.com', '', 9283, 30, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus) VALUES(3, 'juan', 'panchogro', '2023-07-12', '64747', 'juan@mail.com', '', 4587, 30, true);

INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('supremo', 'kaiosama', 'sokaio@mail.com', 'supkaio', '$2a$10$lm8H0.J3pi1FpM3s3CUpe.Ym0FnlwHFclzGIGoWItLCN1vIFWEMmm', 1);
INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('andres', 'gordo', 'angor@mail.com', 'elgordo', '$2a$10$g2//5daw/XMhgjPbkryr7OL5h05zauUHrNhBL8ey6V0NNQHV/3dGm', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 2);