package com.finale.neulhaerang.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.finale.neulhaerang.domain.member.dto.request.CharacterModifyReqDto;

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

	private int backpack;

	private int glasses;

	private int hat;

	private int scarf;

	public static CharacterInfo create(Member member) {
		return CharacterInfo.builder()
			.member(member)
			.build();
	}

	public void updateCharacterInfo(CharacterModifyReqDto characterModifyReqDto) {
		this.backpack = characterModifyReqDto.getBackpack();
		this.hat = characterModifyReqDto.getHat();
		this.scarf = characterModifyReqDto.getScarf();
		this.glasses = characterModifyReqDto.getGlasses();
	}
}
