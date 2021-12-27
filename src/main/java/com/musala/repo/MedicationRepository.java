package com.musala.repo;


import com.musala.model.Drone;
import com.musala.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    /*/ where trip_id 2 /*/
    @Query("SELECT SUM(weight) from Medication WHERE drone_id = :id AND shipping_id = :shipping_id")
    int checkWeight(int id,String shipping_id);

    //@Query("from Medication JOIN Drone ON Drone.id = Medication.drone_id  WHERE Medication.drone_id = :id AND Drone.state ='LOADED' ")
    @Query("from Medication WHERE drone_id = :id")
    List<Medication> loadedDrone(int id);
}
