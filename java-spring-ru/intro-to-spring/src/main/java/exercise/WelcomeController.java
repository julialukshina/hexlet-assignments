package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController{
    @GetMapping
    public String getWelcome(){
        return "Welcome to Spring";
    }

    @GetMapping("/hello")
    public String getHello(@RequestParam ( defaultValue = "World") String name){
        return "Hello, " + name;
    }
}
// END
