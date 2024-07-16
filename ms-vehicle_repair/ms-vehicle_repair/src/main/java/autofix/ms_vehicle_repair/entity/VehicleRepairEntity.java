package autofix.ms_vehicle_repair.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class VehicleRepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_ingreso")

    String fecha_ingreso;

    @Column(name = "hora_ingreso")
    String hora_ingreso;

    @Column(name = "monto_reparaciones")
    String monto_reparaciones;

    @Column(name = "monto_recargos")
    String monto_recargos;

    @Column(name = "monto_descuentos")
    String monto_descuentos;

    @Column(name = "monto_iva")
    String monto_iva;

    @Column(name = "monto_total")
    String monto_total;

    @Column(name = "fecha_salida")
    String fecha_salida;

    @Column(name = "hora_salida")
    String hora_salida;

    @Column(name = "fecha_retiro")
    String fecha_retiro;

    @Column(name = "hora_retiro")
    String hora_retiro;

    private Integer vehicleId;
}
