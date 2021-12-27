package com.musala.repo;

import com.musala.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("from Drone WHERE id = :id")
    Drone droneState(int id);

    @Query("SELECT state from Drone WHERE state ='IDLE' AND id = :id")
    String updateDroneState(int id);

    @Query("from Drone WHERE state ='IDLE'")
    List<Drone> availableDrone();

    @Query("SELECT E.battery from Drone E WHERE id = :id")
    Object batterLevel(int id);

    @Query("SELECT E.battery from Drone E WHERE id = :id")
    int isBatterlevelBelow25(int id);
}
