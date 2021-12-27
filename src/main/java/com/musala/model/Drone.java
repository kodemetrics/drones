package com.musala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.musala.utils.DroneModel;
import com.musala.utils.DroneState;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Entity
public class Drone {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public int id;

 @Column(unique = true)
 @Length(max = 100,message = "100 characters max")
 @Pattern(regexp = "^[a-z0-9_\\-]*" ,message = "serial_number accepts letters and number with only _ or - special character")
 public String serial_number; //(100 characters max);

 @Enumerated(EnumType.STRING)
 public DroneModel model ;  //(Lightweight, Middleweight, Cruiserweight, Heavyweight);

 @Max(value = 500,message = "should not exceed 500 grams")
 public int weight ;  // limit (500gr max);

 public int battery;  //capacity (percentage);

 @Enumerated(EnumType.STRING)
 public DroneState state ; //(IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

 @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="drone")
 @JsonBackReference
 private List<Medication> medicationSet;

 public Drone() {}
 public Drone(String serial_number, DroneModel model, int weight, int battery, DroneState state) {
  this.serial_number = serial_number;
  this.model = model;
  this.weight = weight;
  this.battery = battery;
  this.state = state;
 }

}
