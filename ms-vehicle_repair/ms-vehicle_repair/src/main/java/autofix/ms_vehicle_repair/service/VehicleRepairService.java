package autofix.ms_vehicle_repair.service;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;
import autofix.ms_vehicle_repair.models.RepairModel;
import autofix.ms_vehicle_repair.models.VehicleModel;
import autofix.ms_vehicle_repair.repository.VehicleRepairRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class VehicleRepairService {

    @Autowired
    VehicleRepairRepository vehicleRepairRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<VehicleRepairEntity> getAll(){
        return vehicleRepairRepository.findAll();
    }

    public VehicleRepairEntity getVehicleRepairById(int id){
        return vehicleRepairRepository.findById(id).orElse(null);
    }

    public VehicleRepairEntity getVehicleRepairByPatente(String patente){
        return vehicleRepairRepository.findOneByPatente(patente).orElse(null);
    }

    public VehicleRepairEntity save(VehicleRepairEntity historial){
        return vehicleRepairRepository.save(historial);
    }

    public List<VehicleModel> getVehicles(){
        List<?> rawResponse = restTemplate.getForObject("http://ms-vehicle/vehicles/", List.class);
        List<VehicleModel> vehicles = objectMapper.convertValue(
                rawResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleModel.class)
        );
        if (vehicles == null) {
            return new ArrayList<>();
        }
        return vehicles;
    }

    public List<RepairModel> getRepairs(){
        List<RepairModel> repairs = restTemplate.getForObject("http://ms-repairs/repairs/", List.class);
        System.out.println("Buscando reparaciones");
        if (repairs == null) {
            return new ArrayList<>();
        }
        return repairs;
    }

    public VehicleModel getVehicleByPatente(@PathVariable String patente) {
        try {
            VehicleModel vehicle = restTemplate.getForObject("http://ms-vehicle/vehicles/patente/" + patente, VehicleModel.class);
            System.out.println("Buscando vehiculos");
            return vehicle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RepairModel> getRepairsByPatente(String patente){
        try{
            List<?> rawResponse = restTemplate.getForObject("http://ms-repairs/repairs/byVehicle/" + patente, List.class);
            List<RepairModel> repairs = objectMapper.convertValue(
                    rawResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RepairModel.class)
            );
            return repairs;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    public RepairModel crearReparacion(RepairModel repair){
        System.out.println(repair.getValue());
        int valor = calcular_valor_reparacion(repair.getType(),getVehicleByPatente(repair.getPatente()).getTipo_motor());
        System.out.println(repair.getPatente());
        System.out.println(valor);
        System.out.println(getVehicleByPatente(repair.getPatente()).getTipo_motor());
        repair.setValue(valor);
        return restTemplate.postForObject("http://ms-repairs/repairs/", repair, RepairModel.class);
    }

    public VehicleModel crearVehiculo(VehicleModel vehicle){
        vehicle.setEstado("En reparacion");
        return restTemplate.postForObject("http://ms-vehicle/vehicles/", vehicle, VehicleModel.class);
    }



    public void actualizarEstado(String patente, String nuevoEstado) {
        VehicleModel vehicle = new VehicleModel();
        System.out.println("Entrando a actualizar estado VehicleRepairService");
        vehicle.setEstado(nuevoEstado);
        restTemplate.put("http://ms-vehicle/vehicles/actualizarEstado/" + patente, vehicle);
    }

    public VehicleRepairEntity actualizarHistorial(VehicleRepairEntity newHistorial){
        VehicleRepairEntity historial = getVehicleRepairByPatente(newHistorial.getPatente());
        if (historial == null) {
            return null;
        }
        historial.setMonto_reparaciones(newHistorial.getMonto_reparaciones());
        return vehicleRepairRepository.save(historial);
    }



    public Integer suma_total_reparaciones(RepairModel repair){
        int valor = 0;

        valor = repair.getValue();

        return valor;
    }

    public Integer calcular_valor_reparacion(String tipo_reparacion, String tipo_motor){
        System.out.println("Entrando calculo");
        System.out.println(tipo_reparacion);
        System.out.println(tipo_motor);
        int valor = 0;

        if (Objects.equals(tipo_reparacion, "Reparaciones del Sistema de Frenos")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (tipo_motor == "Diesel")){
                valor = 120000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                System.out.println("Entrando calculo hibrido");
                valor = 180000;
                System.out.println(valor);
            }
            if (Objects.equals(tipo_motor, "Electrico")){
                valor = 220000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Servicio del Sistema de Refrigeracion")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel"))){
                valor = 130000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 190000;
            }
            if (Objects.equals(tipo_motor, "Electrico")){
                valor = 230000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparaciones del Motor")) {
            if (Objects.equals(tipo_motor, "Gasolina")){
                valor = 350000;
            }
            if (Objects.equals(tipo_motor, "Diesel")){
                valor = 450000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 700000;
            }
            if (Objects.equals(tipo_motor, "Electrico")){
                valor = 800000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparaciones de la Transmision")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel"))){
                valor = 210000;
            }
            if ((Objects.equals(tipo_motor, "Hibrido")) || (Objects.equals(tipo_motor, "Electrico"))){
                valor = 300000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparacion del Sistema Electrico")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel"))){
                valor = 150000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 200000;
            }
            if (Objects.equals(tipo_motor, "Electrico")){
                valor = 250000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparaciones del Sistema de Escape")) {
            if (Objects.equals(tipo_motor, "Gasolina")){
                valor = 100000;
            }
            if (Objects.equals(tipo_motor, "Diesel")){
                valor = 120000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 450000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparacion de Neumaticos y Ruedas")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel")) || (Objects.equals(tipo_motor, "Hibrido")) || (Objects.equals(tipo_motor, "Electrico"))){
                valor = 100000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparaciones de la Suspension y la Direccion")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel"))){
                valor = 180000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 210000;
            }
            if (Objects.equals(tipo_motor, "Electrico")){
                valor = 250000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparacion del Sistema de Aire Acondicionado y Calefaccion")) {
            if ((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel"))){
                valor = 150000;
            }
            if ((Objects.equals(tipo_motor, "Hibrido")) || (Objects.equals(tipo_motor, "Electrico"))){
                valor = 180000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparaciones del Sistema de Combustible")) {
            if (Objects.equals(tipo_motor, "Gasolina")){
                valor = 130000;
            }
            if (Objects.equals(tipo_motor, "Diesel")){
                valor = 140000;
            }
            if (Objects.equals(tipo_motor, "Hibrido")){
                valor = 220000;
            }
        }
        if (Objects.equals(tipo_reparacion, "Reparacion y Reemplazo del Parabrisas y Cristales")) {
            if (((Objects.equals(tipo_motor, "Gasolina")) || (Objects.equals(tipo_motor, "Diesel")) || (Objects.equals(tipo_motor, "Hibrido")) || (Objects.equals(tipo_motor, "Electrico")))){
                valor = 80000;
            }
        }
        System.out.println("Calculo de valor reparacion:");
        System.out.println(valor);
        return valor;
    }

    public double recargo_kilometraje(int kilometraje, String tipo_vehiculo){
        double recargo = 0;
        if (kilometraje >= 5001 && kilometraje <= 12000){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback"))) {
                recargo = 0.03;
            }
            if((Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))){
                recargo = 0.05;
            }
        }
        if (kilometraje >= 12001 && kilometraje <= 25000){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback"))) {
                recargo = 0.07;
            }
            if((Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))){
                recargo = 0.09;
            }
        }
        if (kilometraje >= 25001 && kilometraje <= 40000){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback")) || (Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))) {
                recargo = 0.12;
            }
        }
        if (kilometraje >= 40001){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback")) || (Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))) {
                recargo = 0.20;
            }
        }
        return recargo;
    }

    public double recargo_antiguedad(int antiguedad, String tipo_vehiculo){
        double recargo = 0;

        if (antiguedad >= 6 && antiguedad <= 10){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback"))) {
                recargo = 0.05;
            }
            if((Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))){
                recargo = 0.07;
            }
        }
        if (antiguedad >= 11 && antiguedad <= 15){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback"))) {
                recargo = 0.09;
            }
            if((Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))){
                recargo = 0.11;
            }
        }
        if (antiguedad >= 16){
            if((Objects.equals(tipo_vehiculo, "Sedan")) || (Objects.equals(tipo_vehiculo, "Hatchback"))) {
                recargo = 0.15;
            }
            if((Objects.equals(tipo_vehiculo, "SUV")) || (Objects.equals(tipo_vehiculo, "Pickup")) || (Objects.equals(tipo_vehiculo, "Furgoneta"))){
                recargo = 0.20;
            }
        }
        return recargo;
    }

//    public List<RepairEntity> getRepairsByVehicleId(int vehicleId){
//        try{
//            List<RepairEntity> repairs = restTemplate.getForObject("http://ms-repairs/repairs/byVehicle/" + vehicleId, List.class);
//            return repairs;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }




}
