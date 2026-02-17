package co.edu.uptc.personservice.controller;

import co.edu.uptc.personservice.dto.PersonDto;
import co.edu.uptc.personservice.dto.PersonRequest;
import co.edu.uptc.personservice.dto.PersonResponse;
import co.edu.uptc.personservice.model.Person;
import co.edu.uptc.personservice.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

   private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public PersonResponse createTask(@RequestBody PersonRequest request){
       return personService.save(request);
       // return "Persona adicionada";
    }

    @GetMapping
    public List<PersonDto> getPeople(){
        return personService.getAll();
    }
}
