package com.kinsight.service;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import com.kinsight.repository.UserRepository;


 

@Component("userDetailsService")

public class CustomUserDetailsService implements UserDetailsService {

   private final UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

 

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String email) {
      return (UserDetails) userRepository.findemail(email);
   }

 

   

}
