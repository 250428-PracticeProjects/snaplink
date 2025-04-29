package com.revature.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tagId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String color;

	@ManyToMany
	@JoinTable(
					name = "url_tags",
					joinColumns = @JoinColumn(name = "tag_id"),
					inverseJoinColumns = @JoinColumn(name = "url_id")
	)
	private List<Url> urls;
}