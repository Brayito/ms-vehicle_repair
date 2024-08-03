package autofix.ms_vehicle_repair.controller;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;

import autofix.ms_vehicle_repair.models.RepairModel;
import autofix.ms_vehicle_repair.models.VehicleModel;
import autofix.ms_vehicle_repair.service.VehicleRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reparation")
public class VehicleRepairController {

    // Crear una instancia de Logger para esta clase
    private static final Logger LOGGER = Logger.getLogger(VehicleRepairController.class.getName());


    @Autowired
    VehicleRepairService vehicleRepairService;

    @GetMapping("/")
    public ResponseEntity<List<VehicleRepairEntity>> getAll() {
        List<VehicleRepairEntity> reparations = vehicleRepairService.getAll();
        if(reparations.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reparations);
    }

    @GetMapping("/vehicles/")
    public ResponseEntity<List<VehicleModel>> getAllVehicles() {
        List<VehicleModel> vehicles = vehicleRepairService.getVehicles();
        if(vehicles.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/repairs/")
    public ResponseEntity<List<RepairModel>> getAllRepairs() {
        List<RepairModel> repairs = vehicleRepairService.getRepairs();
        if(repairs.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/vehicles/{patente}")
    public ResponseEntity<VehicleModel> getAllVehicles(@PathVariable ("patente") String patente) {
        VehicleModel vehicle = vehicleRepairService.getVehicleByPatente(patente);
        if(vehicle == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleRepairEntity> getById(@PathVariable("id") int id) {
        VehicleRepairEntity reparation = vehicleRepairService.getVehicleRepairById(id);
        if(reparation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reparation);
    }

    @GetMapping("/repairsByVehicle/{patente}")
    public ResponseEntity<VehicleRepairEntity> getByPatente(@PathVariable("patente") String patente) {
        VehicleRepairEntity reparation = vehicleRepairService.getVehicleRepairByPatente(patente);
        if(reparation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reparation);
    }

    @PostMapping("/")
    public ResponseEntity<VehicleRepairEntity> save(@RequestBody VehicleRepairEntity reparation) {
        VehicleRepairEntity newReparation = vehicleRepairService.save(reparation);
        return ResponseEntity.ok(newReparation);
    }

    @PostMapping("/repairs/")
    public ResponseEntity<RepairModel> save(@RequestBody RepairModel repair) {
        try {
            // Imprimir los parámetros de repair para debug
            LOGGER.info("Recibido RepairModel: " + repair.toString());

            // Obtener el tipo de motor desde el microservicio de vehículos
            VehicleModel vehicle = vehicleRepairService.getVehicleByPatente(repair.getPatente());
            if (vehicle == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Calcula el valor de la reparación antes de guardarla
            System.out.println(repair.getType());
            System.out.println(vehicle.getTipo_motor());
            //repair.setValue(vehicleRepairService.calcular_valor_reparacion(repair.getType(), vehicle.getTipo_motor()));
            RepairModel newRepair = vehicleRepairService.crearReparacion(repair);
            //newRepair.setValue(200);
            return ResponseEntity.ok(newRepair);
        } catch (HttpClientErrorException e) {
            // Capturar errores específicos del cliente HTTP
            LOGGER.severe("Error de cliente HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            // Capturar cualquier otra excepción
            LOGGER.severe("Error al guardar la reparación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/vehicles/")
    public ResponseEntity<VehicleModel> save(@RequestBody VehicleModel vehicle) {
        try {
            // Calcula el valor de la reparación antes de guardarla
            LOGGER.info("Recibido VehicleModel: " + vehicle.toString());
            VehicleModel newVehicle = vehicleRepairService.crearVehiculo(vehicle);
            return ResponseEntity.ok(newVehicle);
        } catch (Exception e) {
            // Usar la instancia de Logger para registrar el error
            //LOGGER.severe("Error al guardar la reparación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/calcular")
    public String calcular(){
        List<VehicleModel> vehicles = vehicleRepairService.getVehicles();
        for(VehicleModel vehicle:vehicles){
            String patente = vehicle.getPatente();
            String marca = vehicle.getMarca();
            String modelo = vehicle.getModelo();
            String tipo_vehiculo = vehicle.getTipo_vehiculo();
            Integer ano_fabricacion = vehicle.getAno_fabricacion();
            String tipo_motor = vehicle.getTipo_motor();
            Integer num_asientoss = vehicle.getNum_asientos();

            VehicleRepairEntity historial = new VehicleRepairEntity(null,patente,"","",0,0,0,0,0,"","","","");
        }
        return "";
    }



}