package autofix.ms_vehicle_repair.service;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;
import autofix.ms_vehicle_repair.repository.VehicleRepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VehicleRepairService {

    @Autowired
    VehicleRepairRepository vehicleRepairRepository;

    public List<VehicleRepairEntity> getAll(){
        return vehicleRepairRepository.findAll();
    }

    public VehicleRepairEntity getVehicleRepairById(int id){
        return vehicleRepairRepository.findById(id).orElse(null);
    }


    public VehicleRepairEntity save(VehicleRepairEntity repairList){
        return vehicleRepairRepository.save(repairList);
    }




}
