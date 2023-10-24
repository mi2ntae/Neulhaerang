package com.finale.neulhaerang.domain.title.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "earned_title")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EarnedTitle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "earned_title_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = false)
	private Title title;
}
