
SELECT *
FROM (SELECT DISTINCT x.semana , x.habitaciones_mas_solicitadas, y.numero_habitacion as num_hab_mayor
        FROM (
                SELECT A.semana, MAX(A.cantidad_habitacion) as habitaciones_mas_solicitadas
                FROM (
                        SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                        FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                        WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                        GROUP BY to_char(entrada,'ww'), h.num_hab
            ) A
                GROUP BY A.semana
        ) X
                INNER JOIN (
                    SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                    FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                    WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                    GROUP BY to_char(entrada,'ww'), h.num_hab
            ) Y 
ON X.habitaciones_mas_solicitadas = Y.cantidad_habitacion) Z LEFT JOIN HABITACIONES h ON z.num_hab_mayor = h.NUM_HAB;




/*HABITACIONES QUE POR SEMANA SON LOS MAS SOLICITADAS CON SU NUMERO D HABITACION */
SELECT DISTINCT x.semana , x.habitaciones_mas_solicitadas, y.numero_habitacion as num_hab_mayor
FROM (
        SELECT A.semana, MAX(A.cantidad_habitacion) as habitaciones_mas_solicitadas
        FROM (
                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                GROUP BY to_char(entrada,'ww'), h.num_hab
            ) A
        GROUP BY A.semana
        ) X
INNER JOIN (
                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                GROUP BY to_char(entrada,'ww'), h.num_hab
            ) Y 
ON X.habitaciones_mas_solicitadas = Y.cantidad_habitacion
ORDER BY x.semana asc;





/*HABITACIONES QUE POR SEMANA SON LOS MAS SOLICITADAS CON SU NUMERO D HABITACION */
SELECT DISTINCT x.semana , x.habitaciones_menos_solicitadas, y.numero_habitacion as num_hab_menor
FROM (
        SELECT A.semana, MIN(A.cantidad_habitacion) as habitaciones_menos_solicitadas
        FROM (
                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                GROUP BY to_char(entrada,'ww'), h.num_hab
            ) A
        GROUP BY A.semana
        ) X
INNER JOIN (
                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
                GROUP BY to_char(entrada,'ww'), h.num_hab
            ) Y 
ON X.habitaciones_menos_solicitadas = Y.cantidad_habitacion
ORDER BY x.semana asc;





/*HABITACIONES QUE POR SEMANA SON LOS MAS SOLICITADAS*/
SELECT A.semana, MIN(A.cantidad_habitacion) as habitaciones_menos_solicitadas
FROM (SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
GROUP BY to_char(entrada,'ww'), h.num_hab
ORDER BY to_char(entrada,'ww') asc) A
GROUP BY A.semana
ORDER BY A.semana asc;





/*HABITACIONES QUE POR SEMANA SON LOS MENOS SOLICITADAS*/
SELECT A.semana, MAX(A.cantidad_habitacion) as habitaciones_mas_solicitadas
FROM (SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
GROUP BY to_char(entrada,'ww'), h.num_hab
ORDER BY to_char(entrada,'ww') asc) A
GROUP BY A.semana
ORDER BY A.semana asc;




/*Habitaciones agrupado por los numeros de las habitaciones que hay por semana del año*/
SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
GROUP BY to_char(entrada,'ww'), h.num_hab
ORDER BY to_char(entrada,'ww') asc;







/*----------------------------------CONSUMOS---------------------------------------------*/

SELECT *
FROM (SELECT x.semana , x.servicio_menos_consumido, y.servicio as id_servicio_menor
FROM (
        SELECT A.semana, MIN(A.cantidad_servicio) as servicio_menos_consumido
        FROM (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
                FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
                WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
                GROUP BY to_char(fecha,'ww'), p.id_servicio
                ) A 
        GROUP BY A.semana
        ) X
INNER JOIN (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
            FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
            WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
            GROUP BY to_char(fecha,'ww'), p.id_servicio) Y ON X.SERVICIO_MENOS_CONSUMIDO = Y.cantidad_servicio) Y LEFT JOIN servicios s ON s.id = Y.id_servicio_menor
            ORDER BY Y.semana;


/*SERVICIOS QUE POR SEMANA SON LOS MENOS CONSUMIDOS CON ID DEL SERVICIO*/
SELECT x.semana , x.servicio_menos_consumido, y.servicio as id_servicio_menor
FROM (
        SELECT A.semana, MIN(A.cantidad_servicio) as servicio_menos_consumido
        FROM (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
                FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
                WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
                GROUP BY to_char(fecha,'ww'), p.id_servicio
                ) A 
        GROUP BY A.semana
        ) X
INNER JOIN (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
            FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
            WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
            GROUP BY to_char(fecha,'ww'), p.id_servicio) Y ON X.SERVICIO_MENOS_CONSUMIDO = Y.cantidad_servicio
ORDER BY x.semana asc;



/*SERVICIOS QUE POR SEMANA SON LOS MAS CONSUMIDOS CON ID DEL SERVICIO*/
SELECT DISTINCT x.semana , x.servicio_mas_consumido, y.servicio as id_servicio_mayor
FROM (
        SELECT A.semana, MAX(A.cantidad_servicio) as servicio_mas_consumido
        FROM (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
                FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
                WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
                GROUP BY to_char(fecha,'ww'), p.id_servicio
                ) A 
        GROUP BY A.semana
        ) X
INNER JOIN (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
            FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
            WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
            GROUP BY to_char(fecha,'ww'), p.id_servicio) Y ON X.SERVICIO_MAS_CONSUMIDO = Y.cantidad_servicio
ORDER BY x.semana asc;




/*SERVICIOS QUE POR SEMANA SON LOS MAS CONSUMIDOS*/
SELECT A.semana, MAX(A.cantidad_servicio) as servicio_mas_consumido
FROM (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
        FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
        WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
        GROUP BY to_char(fecha,'ww'), p.id_servicio
        ORDER BY to_char(fecha,'ww') asc) A 
GROUP BY A.semana;




SELECT A.semana, MIN(A.cantidad_servicio) as servicio_menos_consumido
FROM (SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
        FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
        WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
        GROUP BY to_char(fecha,'ww'), p.id_servicio
        ORDER BY to_char(fecha,'ww') asc) A 
GROUP BY A.semana;



/*Consumos agrupado por los servicios que hay por semana del año*/
SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
GROUP BY to_char(fecha,'ww'), p.id_servicio;
