package autofix.ms_vehicle_repair.controller;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;

import autofix.ms_vehicle_repair.service.VehicleRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparation")
public class VehicleRepairController {

    @Autowired
    VehicleRepairService vehicleRepairService;

    @GetMapping("/")
    public ResponseEntity<List<VehicleRepairEntity>> getAll() {
        List<VehicleRepairEntity> reparations = vehicleRepairService.getAll();
        if(reparations.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reparations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleRepairEntity> getById(@PathVariable("id") int id) {
        VehicleRepairEntity reparation = vehicleRepairService.getVehicleRepairById(id);
        if(reparation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reparation);
    }

    @PostMapping("/")
    public ResponseEntity<VehicleRepairEntity> save(@RequestBody VehicleRepairEntity reparation) {
        VehicleRepairEntity newReparation = vehicleRepairService.save(reparation);
        return ResponseEntity.ok(newReparation);
    }

}