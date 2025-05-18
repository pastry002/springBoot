package com.boot.app.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.app.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserInfo create(String username, String email, String password) {
		
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		
		return user;
	}
	
	public UserInfo getUser(String username) {
		Optional<UserInfo> user = this.userRepository.findByusername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new DataNotFoundException("user not found");
		}
	}
}
