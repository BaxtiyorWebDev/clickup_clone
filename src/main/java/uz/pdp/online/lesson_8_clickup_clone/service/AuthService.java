package uz.pdp.online.lesson_8_clickup_clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_8_clickup_clone.entity.User;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.SystemRoleName;
import uz.pdp.online.lesson_8_clickup_clone.payload.ApiResponse;
import uz.pdp.online.lesson_8_clickup_clone.payload.LoginDto;
import uz.pdp.online.lesson_8_clickup_clone.payload.RegisterDto;
import uz.pdp.online.lesson_8_clickup_clone.repository.UserRepos;
import uz.pdp.online.lesson_8_clickup_clone.security.JwtProvider;

import java.util.*;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepos userRepos;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existsByEmail = userRepos.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Bunday foydalanuvchi MO da mavjud", false);

        User user = new User(
                registerDto.getFullName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                SystemRoleName.SYSTEM_USER
        );// HAR QANDAY RO'YXATDAN O'TADIGAN ODAM ODDIY USER BO'LADI

        int code = new Random().nextInt(100_000);
        user.setEmailCode(String.valueOf(code).substring(0, 4));
        userRepos.save(user);
        sendEmail(registerDto.getEmail(), user.getEmailCode());

        return new ApiResponse("Foydalanuvchi tizimdan ro'yxatdan o'tdi, e-pochtasiga tasdiqlash uchun xabar yuborildi", true);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(user.getEmail());
            return new ApiResponse("Token", true, token);
        } catch (Exception e) {
            return new ApiResponse("Parol yoki email xato", false);
        }
    }

    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Clickup@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Tizimga kirishni tasdiqlash");
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepos.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmailCode()==null) {
                return new ApiResponse("Siz allaqachon ro'yxatdan o'tkansiz",false);
            } else if (user.getEmailCode().equals(emailCode)){
                user.setEnabled(true);
                user.setEmailCode(null);
                userRepos.save(user);
                return new ApiResponse("Akkount aktivlashtirildi", true);
            }
            return new ApiResponse("Kod xato", true);
        }
        return new ApiResponse("Bunday user topilmadi", true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepos.findByEmail(username);
        if (optionalUser.isPresent())
            return optionalUser.get();
        throw new UsernameNotFoundException(username + " topilmadi");
    }
}
