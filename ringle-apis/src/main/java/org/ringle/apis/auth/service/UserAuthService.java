package org.ringle.apis.auth.service;

import org.ringle.apis.auth.exception.UserErrorCode;
import org.ringle.apis.auth.exception.UserNotFoundException;
import org.ringle.domain.user.User;
import org.ringle.domain.user.UserRepository;
import org.ringle.domain.user.vo.UserIdentity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserIdentity findUserByEmailAndValidatePassword(String email, String rawPassword) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

		log.info("wat {}", passwordEncoder.encode(rawPassword));

		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new UserNotFoundException(UserErrorCode.INVALID_PASSWORD);
		}

		return UserIdentity.newInstance(user);
	}

	public UserIdentity findUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

		return UserIdentity.newInstance(user);
	}
}
