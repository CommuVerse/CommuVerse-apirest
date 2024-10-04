package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with nickname: " + nickname));

        return new org.springframework.security.core.userdetails.User(
                user.getNickName(),
                user.getPassword(),

                new ArrayList<>()
        );
    }
}
