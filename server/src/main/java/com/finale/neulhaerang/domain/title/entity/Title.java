package com.finale.neulhaerang.domain.title.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Title {
	@Id
	@Column(name = "title_id")
	private long id;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(nullable = false)
	private String content;

}
