package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponce;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponce registerUser(RegisterDto registerDto){

        //Biznes logikani yozamiz

        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());

        if (existsByEmail){
            return new ApiResponce("Bunday email allaqachon mavjud",false);
        }


        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmailCode(UUID.randomUUID().toString());
        user.setEnabled(true);
        userRepository.save(user);


        return  new ApiResponce("Muvafaqqiyatli ro'yxatdan o'tdingiz.",true);



    }



    public ApiResponce login(LoginDto loginDto){





        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                    loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername());

            return new ApiResponce("Token",true,token);
        }catch (BadCredentialsException exception) {
            return new ApiResponce("Parol yoki login xato",false);

        }
    }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+"topilmadi"));
    }
}
