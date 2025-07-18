package org.ringle.apis.user.service;

import org.ringle.apis.auth.exception.UserErrorCode;
import org.ringle.apis.auth.exception.UserNotFoundException;
import org.ringle.domain.user.User;
import org.ringle.domain.user.UserRepository;
import org.ringle.domain.user.vo.UserIdentity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public UserIdentity findUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

		return UserIdentity.newInstance(user);
	}
}
