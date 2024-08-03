package autofix.ms_vehicle_repair.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
public class RepairModel {
    private String type;
    private Integer value;
    private String fecha;
    private String hora;
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
