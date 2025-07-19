package org.ringle.apis.membership.exception;

import org.ringle.globalutils.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MembershipErrorCode implements BaseErrorCode {

	MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBERSHIP_001", "멤버십 정보가 존재하지 않습니다."),
	MEMBERSHIP_EXPIRED(HttpStatus.BAD_REQUEST, "MEMBERSHIP_002", "멤버십이 만료되었습니다."),
	INVALID_MEMBERSHIP_STATUS(HttpStatus.BAD_REQUEST, "MEMBERSHIP_003", "멤버십 상태가 올바르지 않습니다."),

	MEMBERSHIP_FORBIDDEN(HttpStatus.FORBIDDEN, "MEMBERSHIP_004", "해당 멤버십에 대한 접근 권한이 없습니다."),
	ALREADY_ACTIVATED_MEMBERSHIP_EXISTS(HttpStatus.FORBIDDEN, "MEMBERSHIP_005", "이미 활성화된 멤버십이 존재합니다."),
	NO_ACTIVATED_MEMBERSHIP(HttpStatus.NOT_FOUND, "MEMBERSHIP_006", "활성화된 멤버십이 없습니다."),
	CONVERSATION_LIMIT_EXCEEDED(HttpStatus.FORBIDDEN, "MEMBERSHIP_007", "대화 횟수를 모두 소진했습니다."),

	USER_MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_MEMBERSHIP_001", "해당 유저의 멤버십 정보가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
