package com._52org.gardenoftheheart.security.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final GardenRepository gardenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return gardenRepository.findByGardenName(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 텃밭을 찾을 수 없습니다."));

    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Garden garden) {

        return User.builder()
                .username(garden.getUsername())
                .password(garden.getPassword())
                .roles(garden.getRoles().toArray(new String[0]))
                .build();

    }

}
