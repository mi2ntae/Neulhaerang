package com.finale.neulhaerang.domain.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.finale.neulhaerang.global.util.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private long id;

	@Column(length = 40, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private long kakaoId;

	private LocalDateTime withdrawalDate;

	private long titleId;

	public static Member create(long kakaoId, String nickname) {
		return Member.builder()
			.nickname(nickname)
			.kakaoId(kakaoId).build();
	}

	public void updateWithdrawalDate(LocalDateTime now) {
		this.withdrawalDate = now;
	}

	public void updateTitleId(Long titleId) {
		this.titleId = titleId;
	}
}
