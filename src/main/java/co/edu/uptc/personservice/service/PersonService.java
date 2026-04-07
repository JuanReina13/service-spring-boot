package co.edu.uptc.personservice.service;

import java.net.InetAddress;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.uptc.personservice.dto.PersonDto;
import co.edu.uptc.personservice.dto.PersonRequest;
import co.edu.uptc.personservice.dto.PersonResponse;
import co.edu.uptc.personservice.dto.PersonWrapper;
import co.edu.uptc.personservice.model.Person;
import co.edu.uptc.personservice.model.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final String serverIp;

    @Value("${configuration.instance-name}")
    private String instance;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            ip = "IP-desconocida";
        }
        this.serverIp = ip;
    }

    public PersonWrapper<List<PersonDto>> getAll() {
        List<PersonDto> dtos = personRepository.findAll().stream().map(person -> {
            PersonDto dto = new PersonDto();
            dto.setName(person.getName());
            dto.setLastName(person.getLastName());
            dto.setAge(person.getAge());
            return dto;
        }).toList();
        return new PersonWrapper<>(this.serverIp, this.instance, dtos);
    }

    public PersonResponse save(PersonRequest request) {
        Person newPerson = new Person(null, request.getName(), request.getLastName(), request.getAge());
        Person saved = personRepository.save(newPerson);

        PersonResponse response = new PersonResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setLastName(saved.getLastName());
        response.setAge(saved.getAge());
        response.setMessage("Registrado exitosamente en PostgreSQL");
        response.setServerIp(this.serverIp);

        return response;
    }
}