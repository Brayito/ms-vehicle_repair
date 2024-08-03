package autofix.ms_vehicle_repair.service;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;
import autofix.ms_vehicle_repair.models.RepairModel;
import autofix.ms_vehicle_repair.models.VehicleModel;
import autofix.ms_vehicle_repair.repository.VehicleRepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VehicleRepairService {

    @Autowired
    VehicleRepairRepository vehicleRepairRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<VehicleRepairEntity> getAll(){
        return vehicleRepairRepository.findAll();
    }

    public VehicleRepairEntity getVehicleRepairById(int id){
        return vehicleRepairRepository.findById(id).orElse(null);
    }

    public VehicleRepairEntity getVehicleRepairByPatente(String patente){
        return vehicleRepairRepository.findOneByPatente(patente).orElse(null);
    }

    public VehicleRepairEntity save(VehicleRepairEntity repairList){
        return vehicleRepairRepository.save(repairList);
    }

    public List<VehicleModel> getVehicles(){
        List<VehicleModel> vehicles = restTemplate.getForObject("http://ms-vehicle/vehicles/", List.class);
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

    public List<RepairModel> getRepairsByPatente(@PathVariable String patente){
        try{
            List<RepairModel> repairs = restTemplate.getForObject("http://ms-repairs/repairs/byVehicle/" + patente, List.class);
            return repairs;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    public RepairModel crearReparacion(RepairModel repair){
        System.out.println(repair.getValue());
        return restTemplate.postForObject("http://ms-repairs/repairs/", repair, RepairModel.class);
    }

    public VehicleModel crearVehiculo(VehicleModel vehicle){
        return restTemplate.postForObject("http://ms-vehicle/vehicles/", vehicle, VehicleModel.class);
    }



//    calcular_valor_reparacion(String tipo_reparacion, String tipo_motor){
//
//    }

//    private RepairModel calcularReparacion(String patente, String tipo_reparacion){
//        double valor = 0;
//        valor = valor_reparacion(tipo_reparacion,getVehicleByPatente(patente).getTipo_motor());
//        RepairModel repair = new RepairModel(tipo_reparacion, (int) valor, "01-08","13:00", patente);
//        return repair;
//    }

    public Integer calcular_valor_reparacion(String tipo_reparacion, String tipo_motor){
        int valor = 0;

        if (tipo_reparacion == "Reparaciones del Sistema de Frenos") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 120000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 180000;
            }
            if (tipo_motor == "Electrico"){
                valor = 220000;
            }
        }
        if (tipo_reparacion == "Servicio del Sistema de Refrigeracion") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 130000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 190000;
            }
            if (tipo_motor == "Electrico"){
                valor = 230000;
            }
        }
        if (tipo_reparacion == "Reparaciones del Motor") {
            if (tipo_motor == "Gasolina"){
                valor = 350000;
            }
            if (tipo_motor == "Diesel"){
                valor = 450000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 700000;
            }
            if (tipo_motor == "Electrico"){
                valor = 800000;
            }
        }
        if (tipo_reparacion == "Reparaciones de la Transmision") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 210000;
            }
            if ((tipo_motor == "Hibrido") || (tipo_motor == "Electrico")){
                valor = 300000;
            }
        }
        if (tipo_reparacion == "Reparacion del Sistema Electrico") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 150000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 200000;
            }
            if (tipo_motor == "Electrico"){
                valor = 250000;
            }
        }
        if (tipo_reparacion == "Reparaciones del Sistema de Escape") {
            if (tipo_motor == "Gasolina"){
                valor = 100000;
            }
            if (tipo_motor == "Diesel"){
                valor = 120000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 450000;
            }
        }
        if (tipo_reparacion == "Reparacion de Neumaticos y Ruedas") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel") || (tipo_motor == "Hibrido") || (tipo_motor == "Electrico")){
                valor = 100000;
            }
        }
        if (tipo_reparacion == "Reparaciones de la Suspension y la Direccion") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 180000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 210000;
            }
            if (tipo_motor == "Electrico"){
                valor = 250000;
            }
        }
        if (tipo_reparacion == "Reparacion del Sistema de Aire Acondicionado y Calefaccion") {
            if ((tipo_motor == "Gasolina") || (tipo_motor == "Diesel")){
                valor = 150000;
            }
            if ((tipo_motor == "Hibrido") || (tipo_motor == "Electrico")){
                valor = 180000;
            }
        }
        if (tipo_reparacion == "Reparaciones del Sistema de Combustible") {
            if (tipo_motor == "Gasolina"){
                valor = 130000;
            }
            if (tipo_motor == "Diesel"){
                valor = 140000;
            }
            if (tipo_motor == "Hibrido"){
                valor = 220000;
            }
        }
        if (tipo_reparacion == "Reparacion y Reemplazo del Parabrisas y Cristales") {
            if (((tipo_motor == "Gasolina") || (tipo_motor == "Diesel") || (tipo_motor == "Hibrido") || (tipo_motor == "Electrico"))){
                valor = 80000;
            }
        }

        System.out.println(valor);
        return valor;
    }

    public double recargo_kilometraje(int kilometraje, String tipo_vehiculo){
        double recargo = 0;
        if (kilometraje >= 50001 && kilometraje <= 12000){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback")) {
                recargo = 0.03;
            }
            if((tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")){
                recargo = 0.05;
            }
        }
        if (kilometraje >= 12001 && kilometraje <= 25000){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback")) {
                recargo = 0.07;
            }
            if((tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")){
                recargo = 0.09;
            }
        }
        if (kilometraje >= 250001 && kilometraje <= 40000){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback") || (tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")) {
                recargo = 0.12;
            }
        }
        if (kilometraje >= 40001){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback") || (tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")) {
                recargo = 0.20;
            }
        }
        return recargo;
    }

    public double recargo_antiguedad(int antiguedad, String tipo_vehiculo){
        double recargo = 0;

        if (antiguedad >= 6 && antiguedad <= 10){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback")) {
                recargo = 0.05;
            }
            if((tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")){
                recargo = 0.07;
            }
        }
        if (antiguedad >= 11 && antiguedad <= 15){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback")) {
                recargo = 0.09;
            }
            if((tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")){
                recargo = 0.11;
            }
        }
        if (antiguedad >= 16){
            if((tipo_vehiculo == "Sedan") || (tipo_vehiculo == "Hatchback")) {
                recargo = 0.15;
            }
            if((tipo_vehiculo == "SUV") || (tipo_vehiculo == "Pickup") || (tipo_vehiculo == "Furgoneta")){
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
