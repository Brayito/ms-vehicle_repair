package autofix.ms_vehicle_repair.controller;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;

import autofix.ms_vehicle_repair.models.RepairModel;
import autofix.ms_vehicle_repair.models.VehicleModel;
import autofix.ms_vehicle_repair.service.VehicleRepairService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reparation")
public class VehicleRepairController {

    // Crear una instancia de Logger para esta clase
    //private static final Logger LOGGER = Logger.getLogger(VehicleRepairController.class.getName());
    private static final Logger logger = LoggerFactory.getLogger(VehicleRepairController.class);


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

    @GetMapping("/vehiclerepairsByVehicle/{patente}")
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
            //LOGGER.info("Recibido RepairModel: " + repair.toString());

            // Obtener el tipo de motor desde el microservicio de vehículos
            VehicleModel vehicle = vehicleRepairService.getVehicleByPatente(repair.getPatente());
            if (vehicle == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Calcula el valor de la reparación antes de guardarla
            System.out.println("Tipo reparacion:");
            System.out.println(repair.getType());
            System.out.println("Tipo motor:");
            System.out.println(vehicle.getTipo_motor());
            //repair.setValue(vehicleRepairService.calcular_valor_reparacion(repair.getType(), vehicle.getTipo_motor()));
            RepairModel newRepair = vehicleRepairService.crearReparacion(repair);
            //newRepair.setValue(200);
            return ResponseEntity.ok(newRepair);
        } catch (HttpClientErrorException e) {
            // Capturar errores específicos del cliente HTTP
            //LOGGER.severe("Error de cliente HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            // Capturar cualquier otra excepción
            //LOGGER.severe("Error al guardar la reparación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/repairsByVehicle/{patente}")
    public ResponseEntity<List<RepairModel>> getRepairsByPatente(@PathVariable("patente") String patente) {
        List<RepairModel> repairs = vehicleRepairService.getRepairsByPatente(patente);
        if(repairs.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(repairs);
    }

    @PostMapping("/vehicles/")
    public ResponseEntity<VehicleModel> save(@RequestBody VehicleModel vehicle) {
        try {
            // Calcula el valor de la reparación antes de guardarla
            //LOGGER.info("Recibido VehicleModel: " + vehicle.toString());
            VehicleModel newVehicle = vehicleRepairService.crearVehiculo(vehicle);
            VehicleRepairEntity historial = new VehicleRepairEntity(null,vehicle.getPatente(),"","",0,0,0,0,0,"","","","");
            VehicleRepairEntity newHistorial = vehicleRepairService.save(historial);
            return ResponseEntity.ok(newVehicle);
        } catch (Exception e) {
            // Usar la instancia de Logger para registrar el error
            //LOGGER.severe("Error al guardar la reparación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/actualizar/{patente}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable("patente") String patente, @RequestBody VehicleModel vehicle) {
        System.out.println("Actualizar estado VehicleRepairController");
        vehicleRepairService.actualizarEstado(patente, vehicle.getEstado());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/historial")
    public String historial() {
        logger.info("Iniciando actualización de historial...");
        try {
            List<VehicleModel> vehicles = vehicleRepairService.getVehicles();
            for (VehicleModel vehicle : vehicles) {
                String patente = vehicle.getPatente();
                String estado = vehicle.getEstado();
                logger.info("Procesando vehículo: patente={}, estado={}", patente, estado);

                if (Objects.equals(estado, "Retirado")) {
                    Integer valor_reparaciones = 0;
                    List<RepairModel> repairs = vehicleRepairService.getRepairsByPatente(patente);
                    for (RepairModel repair : repairs) {
                        valor_reparaciones += repair.getValue();
                    }
                    VehicleRepairEntity historial = vehicleRepairService.getVehicleRepairByPatente(patente);
                    historial.setMonto_reparaciones(valor_reparaciones);
                    vehicleRepairService.actualizarHistorial(historial);
                    logger.info("Actualizado historial para patente={}: monto_reparaciones={}", patente, valor_reparaciones);
                }
            }
            return "Historial actualizado";
        } catch (Exception e) {
            logger.error("Error actualizando el historial", e);
            return "Error actualizando el historial: " + e.getMessage();
        }
    }



}