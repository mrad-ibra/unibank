package com.unibank.demo.security.service;

import com.unibank.demo.entity.User;
import com.unibank.demo.security.repos.UserRepository;
import com.unibank.demo.security.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByPin(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserByPin(String pin) {
        //User user = userRepository.findById(id).get();
        User user = userRepository.findUserByPin(pin);
        return JwtUserDetails.create(user);
    }
}
