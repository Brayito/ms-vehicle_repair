package autofix.ms_vehicle_repair.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModel {
    private String patente;
    private String marca;
    private String modelo;
    private String tipo_vehiculo;
    private Integer ano_fabricacion;
    private String tipo_motor;
    private Integer num_asientos;
    private Integer kilometraje;

    @Override
    public String toString() {
        return "VehicleModel{" +
                "patente='" + patente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo_vehiculo='" + tipo_vehiculo + '\'' +
                ", ano_fabricacion='" + ano_fabricacion + '\'' +
                ", tipo_motor='" + tipo_motor + '\'' +
                ", num_asientos='" + num_asientos + '\'' +
                '}';
    }
}
