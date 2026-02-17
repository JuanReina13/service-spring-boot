package co.edu.uptc.personservice.service;

import co.edu.uptc.personservice.dto.PersonDto;
import co.edu.uptc.personservice.dto.PersonRequest;
import co.edu.uptc.personservice.dto.PersonResponse;
import co.edu.uptc.personservice.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PersonService {
    private final List<Person> people = new ArrayList<>();
    private Long currentId = 1L;

    public List<PersonDto> getAll(){

        return people.stream().map(person -> {
            PersonDto dto = new PersonDto();
            dto.setName(person.getName());
            dto.setLastName(person.getLastName());
            dto.setAge(person.getAge());
            return dto;
        }).toList();
    }

    public PersonResponse save(PersonRequest request){
        Person newPerson = new Person(currentId++, request.getName(), request.getLastName(), request.getAge());
        people.add(newPerson);

        PersonResponse response = new PersonResponse();
        response.setId(newPerson.getId());
        response.setName(newPerson.getName());
        response.setLastName(newPerson.getLastName());
        response.setAge(newPerson.getAge());
        response.setMessage("Persona Registrada");

        return response;
    }


}
