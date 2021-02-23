package com.utcn.is.NiceCars.dto;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDto {

	@Size(min = 10, max = 40, message = "Title length must be between 10 and 40.")
	private String title;

	@Size(min = 30, message = "Minimum length for description is 30 characters.")
	private String description;

	@Min(value = 0, message = "Minimum value accepted is 0.")
	private float price;

	@NotEmpty(message = "You must select a make.")
	private String make;

	@NotEmpty(message = "You must introduce a model.")
	private String model;

	@Size(min = 2, message = "You must add at least 2 photos.")
	@Size(max = 10, message = "You can add at most 10 photos.")
	private MultipartFile[] photos;

	@NotEmpty(message = "You must select a county.")
	private String county;

	@NotEmpty(message = "You must introduce a locality.")
	private String locality;

	private String modelGeneration;

	private String color;

	@NotNull(message = "You must select the fabrication date.")
	private YearMonth fabricationDate;

	@Min(value = 0, message = "0 is the minimum value accepted.")
	private int kilometers;

	@Min(value = 0, message = "0 is the minimum value accepted.")
	private int horsepower;

	@Min(value = 49, message = "49 is the minimum value accepted.")
	private int displacement;

	private String features;

	@Min(value = 0, message = "0 is the minimum value accepted.")
	private float consumption;

	private String fuel;

	private String body;

	private String gearbox;

	private String powertrain;

	private String emissions;

	public static final List<String> CAR_MAKES = Arrays.asList("Abarth", "Alfa Romeo", "Aston Martin", "Audi",
			"Bentley", "BMW", "Bugatti", "Cadillac", "Chevrolet", "Chrysler", "Citroën", "Dacia", "Daewoo", "Daihatsu",
			"Dodge", "Donkervoort", "DS", "Ferrari", "Fiat", "Fisker", "Ford", "Honda", "Hummer", "Hyundai", "Infiniti",
			"Iveco", "Jaguar", "Jeep", "Kia", "KTM", "Lada", "Lamborghini", "Lancia", "Land Rover", "Landwind", "Lexus",
			"Lotus", "Maserati", "Maybach", "Mazda", "McLaren", "Mercedes-Benz", "MG", "Mini", "Mitsubishi", "Morgan",
			"Nissan", "Opel", "Peugeot", "Porsche", "Renault", "Rolls-Royce", "Rover", "Saab", "Seat", "Skoda", "Smart",
			"SsangYong", "Subaru", "Suzuki", "Tesla", "Toyota", "Volkswagen", "Volvo", "Other");

	public static final List<String> COUNTIES = Arrays.asList("ALBA", "ARAD", "ARGES", "BACAU", "BIHOR",
			"BISTRITA-NASAUD", "BOTOSANI", "BRAILA", "BRASOV", "BUCURESTI", "BUZAU", "CALARASI", "CARAS-SEVERIN",
			"CLUJ", "CONSTANTA", "COVASNA", "DAMBOVITA", "DOLJ", "GALATI", "GIURGIU", "GORJ", "HARGHITA", "HUNEDOARA",
			"IALOMITA", "IASI", "ILFOV", "MARAMURES", "MEHEDINTI", "MURES", "NEAMT", "OLT", "PRAHOVA", "SALAJ",
			"SATU MARE", "SIBIU", "SUCEAVA", "TELEORMAN", "TIMIS", "TULCEA", "VASLUI", "VALCEA", "VRANCEA",
			"OUTSIDE OF ROMANIA");

	public static final List<String> FUELS = Arrays.asList("Diesel", "Gasoline", "Electric", "Hybrid", "LPG");

	public static final List<String> BODIES = Arrays.asList("Sedan", "Break", "Hatchback", "Coupe", "Cabrio", "Pickup",
			"SUV", "Off-Road", "Monovolum", "Minibus");

	public static final List<String> GEARBOXES = Arrays.asList("Manual", "Auto", "SemiAuto");

	public static final List<String> POWERTRAINS = Arrays.asList("AWD", "FWD", "RWD", "4X4");

	public static final List<String> EMISSIONS = Arrays.asList("Euro1", "Euro2", "Euro3", "Euro4", "Euro5", "Euro6",
			"Non-Euro");

}
