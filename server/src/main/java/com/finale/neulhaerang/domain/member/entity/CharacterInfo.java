package com.finale.neulhaerang.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "character_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(length = 50, nullable = false)
	private String face;

	@Column(length = 50)
	private String backpack;

	@Column(length = 50, nullable = false)
	private String skin;

	@Column(length = 50)
	private String glasses;

	@Column(length = 50)
	private String hat;

	@Column(length = 50)
	private String hand;

	@Column(length = 50)
	private String scarf;
}
