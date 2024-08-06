package autofix.ms_vehicle_repair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class VehicleRepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name = "patente")
    String patente;

    //@Column(name = "fecha_ingreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fecha_ingreso;

    //@Column(name = "hora_ingreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime hora_ingreso;

    //@Column(name = "monto_reparaciones")
    Integer monto_reparaciones;

    //@Column(name = "monto_recargos")
    Integer monto_recargos;

    //@Column(name = "monto_descuentos")
    Integer monto_descuentos;

    //@Column(name = "monto_iva")
    Integer monto_iva;

    //@Column(name = "monto_total")
    Integer monto_total;

    //@Column(name = "fecha_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fecha_salida;

    //@Column(name = "hora_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime hora_salida;

    //@Column(name = "fecha_retiro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate fecha_retiro;

    //@Column(name = "hora_retiro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime hora_retiro;


}
