package co.edu.uptc.personservice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.uptc.personservice.dto.PersonDto;
import co.edu.uptc.personservice.dto.PersonRequest;
import co.edu.uptc.personservice.dto.PersonResponse;
import co.edu.uptc.personservice.dto.PersonWrapper;
import co.edu.uptc.personservice.model.Person;

@Service
public class PersonService {

    private final String filePath;
    private final String serverIp;
    @Value("${configuration.instance-name}") private String instance;

    public PersonService(@Value("${configuration.directory}") String directory, @Value("${configuration.file-name}") String fileName) {
        this.filePath = directory + "/" + fileName;
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            ip = "IP-desconocida";
        }
        this.serverIp = ip;

        File folder = new File(directory);
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
    }

    public PersonWrapper<List<PersonDto>> getAll() {
        List<Person> people = readFromFile();
        List<PersonDto> dtos = people.stream().map(person -> {
            PersonDto dto = new PersonDto();
            dto.setName(person.getName());
            dto.setLastName(person.getLastName());
            dto.setAge(person.getAge());
            return dto;
        }).toList();
        return new PersonWrapper<>(this.serverIp, this.instance,dtos);
    }

    public PersonResponse save(PersonRequest request) {
        List<Person> people = readFromFile();

        Long newId = people.isEmpty() ? 1L : people.get(people.size() - 1).getId() + 1;
        Person newPerson = new Person(newId, request.getName(), request.getLastName(), request.getAge());

        people.add(newPerson);
        writeToFile(people);

        PersonResponse response = new PersonResponse();
        response.setId(newPerson.getId());
        response.setName(newPerson.getName());
        response.setLastName(newPerson.getLastName());
        response.setAge(newPerson.getAge());
        response.setMessage("Registrado exitosamente en archivo plano (Persistencia TXT)");
        response.setServerIp(this.serverIp);

        return response;
    }

    private void writeToFile(List<Person> people) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Person p : people) {
                writer.println(p.getId() + "," + p.getName() + "," + p.getLastName() + "," + p.getAge());
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo en el archivo: " + e.getMessage());
        }
    }

    private List<Person> readFromFile() {
        List<Person> people = new ArrayList<>();
        File file = new File(this.filePath);

        if (!file.exists()) return people;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    people.add(new Person(
                        Long.parseLong(data[0]),
                        data[1],
                        data[2],
                        Integer.parseInt(data[3])
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
        return people;
    }
}