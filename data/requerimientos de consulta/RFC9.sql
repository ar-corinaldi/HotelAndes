SELECT u.*
FROM (SELECT c.ID_USUARIO, c.TIPO_DOCUMENTO_USUARIO
FROM Servicios s, Productos p, Consumos c
WHERE s.id = p.id_servicio AND p.id = c.id_producto AND s.tipo_servicios = 4 AND 
c.fecha BETWEEN TO_TIMESTAMP('2019-03-15 00:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-05-18 23:59:59.0', 'YYYY-MM-DD HH24:MI:SS.FF') ) A INNER JOIN Usuarios u ON A.ID_USUARIO = u.NUM_IDENTIDAD AND A.TIPO_DOCUMENTO_USUARIO = u.TIPO_DOCUMENTO;



SELECT c.ID_USUARIO, c.TIPO_DOCUMENTO_USUARIO
FROM Servicios s, Productos p, Consumos c
WHERE s.id = p.id_servicio AND p.id = c.id_producto AND s.tipo_servicios = 4 AND 
c.fecha BETWEEN TO_TIMESTAMP('2019-03-15 00:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-05-18 23:59:59.0', 'YYYY-MM-DD HH24:MI:SS.FF');