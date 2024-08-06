package autofix.ms_vehicle_repair.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor@AllArgsConstructor
public class RepairModel {
    private String type;
    private Integer value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hora;
    private String patente;

    @Override
    public String toString() {
        return "RepairModel{" +
                "type='" + type + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", value='" + value + '\'' +
                ", patente='" + patente + '\'' +
                '}';
    }
}
