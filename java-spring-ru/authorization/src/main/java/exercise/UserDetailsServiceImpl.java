package exercise;

import exercise.model.User;
import exercise.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // BEGIN
        // Получаем пользователя из репозитория
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

//        // Получаем роль пользователя
 String userRole = user.getRole().toString();
//
//        // Создаём список полномочий
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(userRole)
        );

        // Создаём новый объект org.springframework.security.core.userdetails.User
        // Передаём туда пароль,
        // имя пользователя (или те данные, которые используются вместо него, например почту или телефон),
        // и список полномочий, которым будет обладать пользователь
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities
        );
        // END
    }
}
