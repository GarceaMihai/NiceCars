package com.utcn.is.NiceCars.model;

import static javax.persistence.FetchType.LAZY;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Announcement implements Comparable<Announcement> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Lob
	private String description;
	private float price;
	private Instant created;
	private boolean active;
	private boolean promoted;
	private String county;
	private String locality;

	private String make;
	private String model;
	private String modelGeneration;
	private String color;
	private YearMonth fabricationDate;
	private int kilometers;
	private int horsepower;
	private int displacement;
	private String features;
	private float consumption;
	private String fuel;
	private String body;
	private String gearbox;
	private String powertrain;
	private String emissions;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
	private List<Photo> photos;

	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
	private List<Comment> comments;

	@Override
	public int compareTo(Announcement o) {
		return this.created.compareTo(o.getCreated());
	}

	public int getCoverIndex() {
		for (Photo photo : this.photos) {
			if (photo.isCover()) {
				return this.photos.indexOf(photo);
			}
		}
		return 0;
	}

}