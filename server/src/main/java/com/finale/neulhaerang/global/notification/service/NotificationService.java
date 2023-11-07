package com.finale.neulhaerang.global.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.notification.dto.request.NotificationReqDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final DeviceRepository deviceRepository;
	private final MemberRepository memberRepository;

	public void sendNotificationByToken(Long memberId, NotificationReqDto notificationReqDto) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
		List<Device> devices = deviceRepository.findDevicesByMember_Id(member.getId());
		if (devices.size() == 0) {
			log.error(member.getNickname() + "(member_id=" + member.getId() + ")님의 device token을 찾을 수 없습니다. 확인 바랍니다.");
		}
		Notification notification = createNotification(notificationReqDto);
		for (Device device : devices) {
			Message message = createMessage(notification, device);

			try {
				firebaseMessaging.send(message);
			} catch (FirebaseMessagingException e) {
				log.error(member.getNickname() + "님의 device(device_token=" + device.getDeviceToken()
					+ ")에 알림을 보내는 것을 실패하였습니다. 확인 바랍니다.");
			}
		}
	}

	private Notification createNotification(NotificationReqDto notificationReqDto) {
		System.out.println(notificationReqDto.getContent());
		System.out.println(notificationReqDto.getTitle());
		return Notification.builder()
			.setTitle(notificationReqDto.getTitle())
			.setBody(notificationReqDto.getContent())
			.build();
	}

	private Message createMessage(Notification notification, Device device) {
		return Message.builder()
			.setToken(device.getDeviceToken())
			.setNotification(notification)
			.build();
	}
}
