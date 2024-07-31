package autofix.ms_vehicle_repair.repository;

import autofix.ms_vehicle_repair.entity.VehicleRepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepairEntity,Integer> {


    Optional<VehicleRepairEntity> findOneByPatente(String patente);
}
