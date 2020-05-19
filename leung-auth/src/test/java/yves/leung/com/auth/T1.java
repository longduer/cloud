package yves.leung.com.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class T1 {
    public static void main(String[] args) {
        System.out.println("h");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("186588");
        System.out.println(encodedPassword);
    }
}
