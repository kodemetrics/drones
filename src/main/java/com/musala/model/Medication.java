package com.musala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Medication {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotNull(message = "cannot be null")
   public int drone_id;

   @Pattern(regexp = "^[a-zA-Z0-9_\\-]*" ,message = "accepts letters,numbers,underscore and hyphen")
   public String name;

   @Max(value = 500,message = "should not exceed 500 grams")
   public int weight;

   @Pattern(regexp = "^[A-Z0-9_\\-]*" ,message = "accepts only upper case letters,numbers,underscore and hyphen")
   public String code;

   public String image;

   @NotNull(message = "cannot be null")
   public String shipping_id;

   @ManyToOne
   @JoinColumn(name="drone_id",insertable = false, updatable = false)
   //@JsonManagedReference
   public Drone drone;


   public Medication() {}

   public Medication(int drone_id, String name, int weight, String code, String image, String shipping_id) {
      this.drone_id = drone_id;
      this.name = name;
      this.weight = weight;
      this.code = code;
      this.image = image;
      this.shipping_id = shipping_id;
   }


}
