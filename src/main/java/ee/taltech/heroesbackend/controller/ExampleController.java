package ee.taltech.heroesbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/finance")
@RestController
public class ExampleController {

    @GetMapping("string")
    public String string(){
        return "Hasta la vista, finance is here";
    }

    @GetMapping("number")
    public Integer number(){
        return 12;
    }

    @GetMapping("user")
    public User user(){
        return new User("Oleg");
    }
}
