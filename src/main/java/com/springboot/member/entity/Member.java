package com.springboot.member.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 *  - 멤버 변수 추가
 *  - lombok 추가
 *  - Spring Data JDBC 엔티티 설정 추가.
 */
@Getter
@Setter
public class Member {
    @Id
    private Long memberId;

    private String email;

    private String name;

    private String phone;
}
