package com.musala.controller;

import com.musala.model.Drone;
import com.musala.model.Medication;
import com.musala.repo.DroneRepository;
import com.musala.repo.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
@Validated
public class Route {

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @GetMapping("")
    public String getHomepage(){
        return "Welcome";
    }

//    @PostMapping("create_new_drone")
//    public ResponseEntity<Drone> create_new_drone(@RequestBody @Valid Drone drone){
//        Drone newDrone = droneRepository.save(drone);
//        return new ResponseEntity<Drone>(newDrone, HttpStatus.CREATED);
//    }

    @PostMapping("create_new_drone")
    @ResponseStatus(HttpStatus.CREATED)
    public Drone create_new_drone(@RequestBody @Valid Drone drone){
        return droneRepository.save(drone) ;
    }

    @PostMapping("save_medication")
    @ResponseStatus(HttpStatus.CREATED)
    public Medication save_medication(@RequestBody @Valid Medication medication){
       Medication saveMedication = null;
       Drone drone = droneRepository.droneState(medication.drone_id);
       /*
       if(drone.state.toString().equals("IDLE")){
           throw new IllegalStateException("drone is idle");
       }else if(drone.state.toString().equals("LOADED")){
           throw new IllegalStateException("drone is loaded");
       }*/
        if(drone.state.toString().equals("IDLE") || drone.state.toString().equals("LOADED")){
            int isBatterLevelBelow25 = droneRepository.isBatterlevelBelow25(medication.drone_id);
            int weight = medicationRepository.checkWeight(medication.drone_id,medication.shipping_id);
            int total = weight + medication.weight;

            if(isBatterLevelBelow25 < 25){
                throw new IllegalStateException("batter level is below 25");
            }else if(total >= 500){
                throw new IllegalStateException("items weight ["+ total +"] exceeds 500 grams");
            }else{
                saveMedication = medicationRepository.save(medication);
            }
        }
        // return medicationRepository.save(medication);
        return  saveMedication;
    }

    @GetMapping("get_all_drone")
    public List<Drone> get_drone(Drone drone){
        return droneRepository.findAll() ;
    }


    @GetMapping("loaded_drone/{id}")
    List<Medication> LoadedDrone (@PathVariable("id") int id){
        return  medicationRepository.loadedDrone(id).stream().filter(medication -> medication.drone.state.toString().equals("LOADED")).collect(Collectors.toList());
    }

    @GetMapping("available_drone")
    List<Drone> checkAvailableDrone (){
        return droneRepository.availableDrone() ;
    }

    @GetMapping("battery_level/{id}")
    Object checkBatteryLevels (@PathVariable("id") int id){
        Object battery_level = droneRepository.batterLevel(id);
        Map<String, String> errors = new HashMap<>();
        errors.put("battery",battery_level.toString());
        return battery_level ;
    }

    @GetMapping("get_medication")
    public List<Medication> getMedication(){
        return medicationRepository.findAll() ;
    }


}
