package autofix.ms_vehicle_repair.model;

import lombok.Data;

@Data
public class Vehicle {

    private String patente;

    private String marca;

    private String modelo;

    private String tipo_vehiculo;

    private Integer ano_fabricacion;

    private String tipo_motor;

    private Integer num_asientos;

    private Integer vehicleRepairId;
}
