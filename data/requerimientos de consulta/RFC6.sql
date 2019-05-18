
SELECT MAX(a.Numero_De_Consumos), a.Semana
FROM (  SELECT COUNT(to_number(to_char(fecha,'WW'))) as Numero_De_Consumos,to_number(to_char(fecha,'WW')) as Semana
        FROM consumos 
        GROUP BY to_number(to_char(fecha,'WW')) 
        ) a
GROUP BY a.semana 
FETCH FIRST 1 ROWS ONLY; 

SELECT COUNT(to_number(to_char(fecha,'WW'))) as Numero_De_Consumos,to_number(to_char(fecha,'WW')) as Semana
FROM consumos 
GROUP BY to_number(to_char(fecha,'WW'));
