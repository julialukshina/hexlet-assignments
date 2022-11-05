package exercise.controller;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN

//    Создайте в контроллере метод, который обрабатывает GET-запросы по адресу /users.
//    Метод должен поддерживать фильтрацию пользователей по параметрам firstName и lastName
//    и возвращать все совпадения по вхождению переданной строки без учета регистра.
//    Параметры для фильтрации передаются в строке запроса. Например запрос на адрес /users?firstName=ale должен вернуть
//    всех пользователей, чьё имя содержит строку "ale".

    @GetMapping
    public Iterable<User> getUsersByFirstNameAndLastName(@RequestParam(required = false) String firstName,
                                                         @RequestParam(required = false) String lastName){
        if (firstName == null && lastName == null) {
            return userRepository.findAll();
        }
        if (firstName == null) {
            return userRepository.findAll(QUser.user.lastName.containsIgnoreCase(lastName));
        }
        if (lastName == null) {
            return userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName));
        }
        return userRepository.findAll(QUser.user.lastName.containsIgnoreCase(lastName)
                .and(QUser.user.firstName.containsIgnoreCase(firstName)));
    }
    
    // END
}

