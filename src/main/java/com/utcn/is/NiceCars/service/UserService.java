package com.utcn.is.NiceCars.service;

import static java.util.Collections.singletonList;

import java.time.Instant;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.utcn.is.NiceCars.dto.UserRegistrationDto;
import com.utcn.is.NiceCars.model.Announcement;
import com.utcn.is.NiceCars.model.User;
import com.utcn.is.NiceCars.repository.UserRepository;
import com.utcn.is.NiceCars.util.Mapper;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
				true, true, true, getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return singletonList(new SimpleGrantedAuthority(role));
	}

	public User save(UserRegistrationDto userRegistrationDto) {
		User user = new User();
		user = (User) Mapper.mapUserRegistrationDto(user, userRegistrationDto, passwordEncoder, false);
		user.setMemberSince(Instant.now());
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	public void deleteAnnouncement(Announcement announcement) {
		User user = userRepository.findByUsername(announcement.getUser().getUsername());
		user.getAnnouncements().remove(announcement);
		userRepository.save(user);
	}

	public User update(Long id, UserRegistrationDto userRegistrationDto) {
		User user = userRepository.getOne(id);
		Mapper.mapUpdatedUser(user, userRegistrationDto);
		return userRepository.save(user);
	}

}
