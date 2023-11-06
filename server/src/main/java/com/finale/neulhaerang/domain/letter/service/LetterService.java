package com.finale.neulhaerang.domain.letter.service;

import java.time.LocalDate;

public interface LetterService {
	String findLetterByDate(LocalDate date);
}
