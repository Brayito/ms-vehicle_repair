package autofix.ms_vehicle_repair.model;

import lombok.Data;

@Data
public class RepairEntity {

    private String type;
    private Integer value;
    private Integer vehicleRepairId;
}
