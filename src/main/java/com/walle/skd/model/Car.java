package com.walle.skd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private String model;
    private String type;
    private int productionYear;
    private String ownerName;
    private String plateNumber;

}
