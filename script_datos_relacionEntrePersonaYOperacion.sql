SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE mensaje;
TRUNCATE TABLE  conversacion;
TRUNCATE TABLE  OPERACION;
TRUNCATE TABLE  ROL;
TRUNCATE TABLE  PERSONA;
TRUNCATE TABLE  CUENTA;
TRUNCATE TABLE  tipo_estado;
TRUNCATE TABLE  tipo_moneda;
TRUNCATE TABLE  tipo_operacion;
TRUNCATE TABLE  TIPO_ROL;
SET FOREIGN_KEY_CHECKS = 1;






/*
-- Query: SELECT * FROM estebanco.persona
LIMIT 0, 1000

-- Date: 2023-03-20 13:37
*/
INSERT INTO persona (`id`,`dni`,`nombre`,`apellido1`,`apellido2`,`correo`,`direccion`,`telefono`,`usuario`,`contraseña`,`estado`) VALUES (NULL,'652456D','Misco','Jones','Smith','misco@gmail.com','C/Demosteles','555666','misco123','1234','espera_confirmacion');
INSERT INTO persona (`id`,`dni`,`nombre`,`apellido1`,`apellido2`,`correo`,`direccion`,`telefono`,`usuario`,`contraseña`,`estado`) VALUES (NULL,'3214','jose','sancg','llex','jose@gmail.com','C/andromeda','133454','jose123','1234','bien');
INSERT INTO persona (`id`,`dni`,`nombre`,`apellido1`,`apellido2`,`correo`,`direccion`,`telefono`,`usuario`,`contraseña`,`estado`) VALUES (NULL,'1243','javi','gran','lui','javi@gmail.com','C/julio','64747','javi123','1234','bien');

/*
-- Query: SELECT * FROM estebanco.conversacion
LIMIT 0, 1000

-- Date: 2023-03-20 13:38
*/
INSERT INTO conversacion (`idconversacion`,`estado`,`fecha_inicio`,`fecha_fin`,`Persona_id`,`Asistente_id`) VALUES (1,1,'2002-01-01 00:00:00','2002-01-01 00:00:00',1,2);
INSERT INTO conversacion (`idconversacion`,`estado`,`fecha_inicio`,`fecha_fin`,`Persona_id`,`Asistente_id`) VALUES (2,1,'2002-01-01 00:00:00','2002-01-01 00:00:00',1,3);

/*
-- Query: SELECT * FROM estebanco.cuenta
LIMIT 0, 1000

-- Date: 2023-03-20 13:31
*/
INSERT INTO cuenta (`IBAN`,`saldo`,`moneda`,`estado`,`fecha_apertura`,`id`) VALUES ('642764C',100,'euro','bien','2002-01-01 00:00:00',NULL);
INSERT INTO cuenta (`IBAN`,`saldo`,`moneda`,`estado`,`fecha_apertura`,`id`) VALUES ('7632567H',1000,'libra','espera_confirmacion','2002-01-01 00:00:00',NULL);
INSERT INTO cuenta (`IBAN`,`saldo`,`moneda`,`estado`,`fecha_apertura`,`id`) VALUES ('5754348G',50,'euro','bloqueado','2002-01-01 00:00:00',NULL);


/*
-- Query: SELECT * FROM estebanco.mensaje
LIMIT 0, 1000

-- Date: 2023-03-20 13:39
*/
INSERT INTO mensaje (`idmensaje`,`fecha_envio`,`texto`,`conversacion_idconversacion`,`conversacion_Emisor_id`,`conversacion_Receptor_id`) VALUES (1,'2002-01-01 00:00:00','hola hdbhwsa',1,1,2);
INSERT INTO mensaje (`idmensaje`,`fecha_envio`,`texto`,`conversacion_idconversacion`,`conversacion_Emisor_id`,`conversacion_Receptor_id`) VALUES (2,'2002-01-01 00:00:00','alberto pesao',1,2,1);
INSERT INTO mensaje (`idmensaje`,`fecha_envio`,`texto`,`conversacion_idconversacion`,`conversacion_Emisor_id`,`conversacion_Receptor_id`) VALUES (3,'2002-01-01 00:00:00','sergio putero',2,1,3);

/*
-- Query: SELECT * FROM estebanco.operacion
LIMIT 0, 1000

-- Date: 2023-03-20 13:34
*/
INSERT INTO operacion (`idOperacion`,`fecha_operacion`,`tipo`,`cantidad`,`moneda`,`IBAN_cuentaDestinoOrigen`,`Cuenta_id`,`Persona_id`) VALUES (1,'2002-01-01 00:00:00','sacar',16,NULL,NULL,1,1);
INSERT INTO operacion (`idOperacion`,`fecha_operacion`,`tipo`,`cantidad`,`moneda`,`IBAN_cuentaDestinoOrigen`,`Cuenta_id`,`Persona_id`) VALUES (2,'2002-01-01 00:00:00','meter',17,NULL,NULL,1,1);


/*
-- Query: SELECT * FROM estebanco.rol
LIMIT 0, 1000

-- Date: 2023-03-20 13:30
*/
INSERT INTO rol (`rol`,`id`,`Persona_id`,`Cuenta_id`,`bloqueado_empresa` ) VALUES ('normal',1,1,1,0);
INSERT INTO rol (`rol`,`id`,`Persona_id`,`Cuenta_id`,`bloqueado_empresa` ) VALUES ('asistente',2,2,2,0);
INSERT INTO rol (`rol`,`id`,`Persona_id`,`Cuenta_id`,`bloqueado_empresa` ) VALUES ('asistente',3,3,3,0);
/*
-- Query: SELECT * FROM estebanco.tipo_estado
LIMIT 0, 1000

-- Date: 2023-03-20 13:37
*/
INSERT INTO tipo_estado (`id`,`nombre`) VALUES (1,'esperando_confirmacion');
INSERT INTO tipo_estado (`id`,`nombre`) VALUES (2,'bien');
INSERT INTO tipo_estado (`id`,`nombre`) VALUES (3,'bloqueado');

/*
-- Query: SELECT * FROM estebanco.tipo_moneda
LIMIT 0, 1000

-- Date: 2023-03-20 13:27
*/
INSERT INTO tipo_moneda (`id`,`moneda`) VALUES (1,'euro');
INSERT INTO tipo_moneda (`id`,`moneda`) VALUES (2,'libra');

/*
-- Query: SELECT * FROM estebanco.tipo_operacion
LIMIT 0, 1000

-- Date: 2023-03-20 13:33
*/
INSERT INTO tipo_operacion (`id`,`nombre`) VALUES (1,'sacar');
INSERT INTO tipo_operacion (`id`,`nombre`) VALUES (2,'meter');
INSERT INTO tipo_operacion (`id`,`nombre`) VALUES (3,'cambio divisa');

/*
-- Query: SELECT * FROM estebanco.tipo_rol
LIMIT 0, 1000

-- Date: 2023-03-20 13:41
*/
INSERT INTO tipo_rol (`idtipo_rol`,`nombre`) VALUES (1,'normal');
INSERT INTO tipo_rol (`idtipo_rol`,`nombre`) VALUES (2,'empresa');
