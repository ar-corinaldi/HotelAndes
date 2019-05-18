/*Habitaciones agrupado por los numeros de las habitaciones que hay por semana del año*/
SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
GROUP BY to_char(entrada,'ww'), h.num_hab
ORDER BY to_char(entrada,'ww') asc;

/*Consumos agrupado por los servicios que hay por semana del año*/
SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio
FROM consumos c INNER JOIN productos p ON c.id_producto = p.id
WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD')
GROUP BY to_char(fecha,'ww'), p.id_servicio
ORDER BY to_char(fecha,'ww') asc;
