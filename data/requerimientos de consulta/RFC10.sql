Select us.*
from usuarios us
left outer join 
    (Select *    
    from consumos con
    inner join (Select prod.id as prodid
                from servicios ser 
                inner join productos prod
                on ser.id = prod.id_servicio
                where ser.tipo_servicios = 4) pro on
    pro.prodid = con.id_producto
    where con.fecha BETWEEN TO_TIMESTAMP('2019-01-01', 'YYYY-MM-DD HH24:MI:SS.FF') 
    AND TO_TIMESTAMP('2019-12-31', 'YYYY-MM-DD HH24:MI:SS.FF'))prods
on prods.id_Usuario = us.num_identidad
where  prods.id_Habitacion is null;