Select resser.id_servicio
     from servicios ser
      inner join reservas_Servicios resser
      on ser.id= resser.id_servicio
      where  (extract (hour from (resser.fecha_inicial - resser.fecha_final)))*-1 >4 
      and ser.tipo_servicios= 8 or ser.tipo_servicios=11  ;