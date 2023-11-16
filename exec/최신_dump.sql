create table character_info (
                                character_id bigint not null auto_increment,
                                backpack integer not null,
                                glasses integer not null,
                                hand integer not null,
                                hat integer not null,
                                scarf integer not null,
                                skin integer not null,
                                title bigint not null,
                                member_id bigint not null,
                                primary key (character_id)
) engine=InnoDB;
create table daily_routine (
                               daily_routine_id bigint not null auto_increment,
                               is_check bit not null,
                               routine_date date not null,
                               status bit not null,
                               routine_id bigint not null,
                               primary key (daily_routine_id)
) engine=InnoDB;
create table device (
                        device_id bigint not null auto_increment,
                        device_token varchar(255) not null,
                        member_id bigint not null,
                        primary key (device_id)
) engine=InnoDB;
create table earned_title (
                              earned_title_id bigint not null auto_increment,
                              member_id bigint not null,
                              title_id bigint not null,
                              primary key (earned_title_id)
) engine=InnoDB;
create table letter (
                        letter_id bigint not null auto_increment,
                        create_date DATETIME not null,
                        content varchar(16376) not null,
                        letter_date date not null,
                        is_read bit default false not null,
                        member_id bigint not null,
                        primary key (letter_id)
) engine=InnoDB;
create table member (
                        member_id bigint not null auto_increment,
                        create_date DATETIME not null,
                        kakao_id bigint not null,
                        nickname varchar(40) not null,
                        withdrawal_date datetime(6),
                        primary key (member_id)
) engine=InnoDB;
create table routine (
                         routine_id bigint not null auto_increment,
                         alarm bit default false not null,
                         alarm_time time,
                         content varchar(100) not null,
                         delete_date date,
                         repeated varchar(7) not null,
                         stat_type varchar(255) not null,
                         member_id bigint not null,
                         primary key (routine_id)
) engine=InnoDB;
create table title (
                       title_id bigint not null,
                       content varchar(255) not null,
                       name varchar(50) not null,
                       primary key (title_id)
) engine=InnoDB;
create table todo (
                      todo_id bigint not null auto_increment,
                      alarm bit default false not null,
                      is_check bit default false not null,
                      content varchar(100) not null,
                      stat_type varchar(255) not null,
                      status bit default false not null,
                      todo_date datetime(6) not null,
                      member_id bigint not null,
                      primary key (todo_id)
) engine=InnoDB;
alter table character_info
    add constraint FKqa65yxhuxch5tm4ohbtvmiipi
        foreign key (member_id)
            references member (member_id);
alter table daily_routine
    add constraint FKdj1umfa92rgs8w4o7io12a18g
        foreign key (routine_id)
            references routine (routine_id);
alter table device
    add constraint FKs2ah6o1y9r1ox99fh8vj5y0ol
        foreign key (member_id)
            references member (member_id);

alter table earned_title
    add constraint FK7s9kvxp1v9llpcd1v344nnpxe
        foreign key (member_id)
            references member (member_id);

alter table earned_title
    add constraint FK2n5otfs92xwv9x7j7h3ok5xra
        foreign key (title_id)
            references title (title_id);
alter table letter
    add constraint FK3dculuua2qufjk2o80ah5b9o8
        foreign key (member_id)
            references member (member_id);
alter table routine
    add constraint FK546lpheu7wdmjm1fj26wpyno2
        foreign key (member_id)
            references member (member_id);
alter table todo
    add constraint FK67o67f2ave0yd2pb137aoh603
        foreign key (member_id)
            references member (member_id);

insert into finale.title (title_id, name, content)
values (1, '무한한 성장의 시작', '갓생력 C+ 달성!'),
       (2, '발전의 끝이 어디예요?', '갓생력 B+ 달성!'),
       (3, '천상천하 네가 최고', '갓생력 A+ 달성!'),
       (4, '혼자도 잘 살아요', '생존력 C+ 달성!'),
       (5, '무인도에서도 살아남을지도?', '생존력 B+ 달성!'),
       (6, '바퀴벌레 베프', '생존력 A+ 달성!'),
       (7, 'I am 인싸예요', '인싸력 C+ 달성!'),
       (8, '아는 형님의, 아는 친척의, 아는 동생의, 아는 해랑이요', '인싸력 B+ 달성!'),
       (9, 'BTS, 손흥민, 해랑이 렛츠고', '인싸력 A+ 달성!'),
       (10, '이제 탕후루는 끊었어요', '튼튼력 C+ 달성!'),
       (11, '쾌걸해랑맨', '튼튼력 B+ 달성!'),
       (12, '도핑테스트 대상자', '튼튼력 A+ 달성!'),
       (13, '예술이 조크든요', '창의력 C+ 달성!'),
       (14, '너 N발 C야?', '창의력 B+ 달성!'),
       (15, '아이디어 수도꼭지', '창의력 A+ 달성!'),
       (16, 'Give Love 사랑이 모자라요', '최애력 C+ 달성!'),
       (17, '사랑비가 내려와', '최애력 B+ 달성!'),
       (18, '숨 참고 love dive', '최애력 A+ 달성!'),
       (19, '슬기로운 늘해랑 생활', '시작이 반이에요. 해랑이와 함께 하루를 꾸려보세요!'),
       (20, '나태 형은 나가있어… 죽기 싫으면', '나태 괴물을 물리친 용사!'),
       (21, '보험은 있죠?', '잘 자고 잘 먹는 게 최고예요!'),
       (22, '해랑이 우표 도둑', '해랑이에게 편지를 10개 받았어요!'),
       (23, '해랑이 연필 파괴자', '해랑이에게 편지를 50개 받았어요!'),
       (24, '해랑이 전완근 트레이너', '해랑이에게 편지를 100개 받았어요!'),
       (25, '갓생살기 딱 좋은 날씨네~', '연속 10일 동안 날씨가 맑음이에요!'),
       (26, '당신을 인간 해바라기로 임명합니다.', '연속 50일 동안 날씨가 맑음이에요!'),
       (27, '솜사탕 수집가', '연속 10일 동안 날씨가 흐려요…'),
       (28, '기상청 테러리스트', '연속 50일 동안 날씨가 흐려요…'),
       (29, '태양을 피하고 싶어서~', '연속 10일 동안 비가 내려요 ㅜㅜ'),
       (30, '우산 없이 못 사는 해랑이', '연속 50일 동안 비가 내려요 ㅜㅜ'),
       (31, '아싸 탈출 넘버원', '처음으로 다른 사람과 교류했어요!')
;
