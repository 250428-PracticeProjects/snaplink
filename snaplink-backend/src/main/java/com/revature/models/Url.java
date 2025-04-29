package com.revature.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "urls")
@Getter
@Setter
@ToString(exclude = {"user", "sessionId"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int urlId;

	@Column(nullable = false)
	private String originalUrl;

	@Column(nullable = false, unique = true)
	private String shortenedUrl;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	private String description;

	@ManyToMany(mappedBy = "urls", cascade = CascadeType.ALL)
	private List<Tag> tags;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column
	private String sessionId;
}