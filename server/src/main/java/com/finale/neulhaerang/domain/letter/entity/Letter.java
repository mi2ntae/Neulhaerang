package com.finale.neulhaerang.domain.letter.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.finale.neulhaerang.domain.member.entity.Member;
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
public class Letter extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "letter_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false, length = 16376)
	private String content;

	@ColumnDefault("false")
	@Column(name = "is_read", nullable = false)
	private boolean read;

	@Column(nullable = false)
	private LocalDate letterDate;

	public static Letter create(Member member, String content, LocalDate date) {
		return Letter.builder()
			.member(member)
			.content(content)
			.letterDate(date)
			.build();
	}

	public void updateRead() {
		this.read = true;
	}
}
