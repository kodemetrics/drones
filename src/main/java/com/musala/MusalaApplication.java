package com.musala;


import com.musala.model.Drone;
import com.musala.model.Medication;
import com.musala.repo.DroneRepository;
import com.musala.repo.MedicationRepository;
import com.musala.utils.DroneModel;
import com.musala.utils.DroneState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class MusalaApplication {
    Logger logger =  LoggerFactory.getLogger(MusalaApplication.class);
    @Autowired
    DroneRepository mdroneRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
        SpringApplication.run(MusalaApplication.class, args);

        DroneRepository droneRepository = configurableApplicationContext.getBean(DroneRepository.class);
        MedicationRepository medicationRepository = configurableApplicationContext.getBean(MedicationRepository.class);

        List<Drone> droneList = new ArrayList<>();
        droneList.add(new Drone("001", DroneModel.Lightweight,500,95, DroneState.LOADED));
        droneList.add(new Drone("002", DroneModel.Middleweight,500,90, DroneState.IDLE));
        droneList.add(new Drone("003", DroneModel.Cruiserweight,500,85, DroneState.IDLE));
        droneList.add(new Drone("004", DroneModel.Heavyweight,500,80, DroneState.IDLE));
        droneList.add(new Drone("005", DroneModel.Lightweight,500,75, DroneState.IDLE));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1,"Panadol",200,"PANADOL_001","picture_001.jpg","111"));
        medications.add(new Medication(1,"Flagil",200,"FLAGIL_001","picture_002.jpg","111"));
        medications.add(new Medication(1,"Chroquine",100,"CHEMO_001","picture_003.jpg","111"));
        medications.add(new Medication(2,"Aspirin",200,"ASPIRIN_001","picture_004.jpg","112"));

        droneRepository.saveAll(droneList);
        medicationRepository.saveAll(medications);
    }

    /* run task every 2min */
    @Scheduled(initialDelay = 1000l,fixedDelay = 2 * 60 * 1000l)
    void Task() throws InterruptedException{
        for (int i = 0; i < mdroneRepository.findAll().size(); i++) {
            int id = mdroneRepository.findAll().get(i).id;
            int battery = mdroneRepository.findAll().get(i).battery;
            logger.info("drone id:"+ id + " battery level: "+battery+ " "+ new Date());
            System.out.println("drone id:"+ id + " battery level: "+battery+ " "+ new Date());
        }
        Thread.sleep(1000l);
    }


}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled",matchIfMissing = true)
class SchedulingConfiguration{

}

