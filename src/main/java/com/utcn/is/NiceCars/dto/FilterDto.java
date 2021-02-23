package com.utcn.is.NiceCars.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
	private List<String> makes;
	private List<String> county;
	private int maxPrice;
	private int oldestFabricationYear;
	private int maxKilometers;
	private int maxDisplacement;
	private int minHorsepower;
	private float maxConsumption;
	private List<String> fuel;
	private List<String> body;
	private List<String> gearbox;
	private List<String> powertrain;
	private List<String> emissions;
}
