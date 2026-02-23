package co.edu.uptc.personservice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value; // Import correcto de Spring
import org.springframework.stereotype.Service;

import co.edu.uptc.personservice.dto.PersonDto;
import co.edu.uptc.personservice.dto.PersonRequest;
import co.edu.uptc.personservice.dto.PersonResponse;
import co.edu.uptc.personservice.model.Person;

@Service
public class PersonService {

    private final String filePath;

    // Inyectamos la ruta en Base64 desde el application.yml
    public PersonService(@Value("${configuracion.ruta-archivo-b64}") String rutaEnBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(rutaEnBase64);
        this.filePath = new String(decodedBytes);
        
        // Crear la carpeta contenedora (ej: ./data) si no existe para evitar errores
        File directory = new File(this.filePath).getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
    }

    public List<PersonDto> getAll() {
        List<Person> people = readFromFile();
        return people.stream().map(person -> {
            PersonDto dto = new PersonDto();
            dto.setName(person.getName());
            dto.setLastName(person.getLastName());
            dto.setAge(person.getAge());
            return dto;
        }).toList();
    }

    public PersonResponse save(PersonRequest request) {
        List<Person> people = readFromFile();
        
        // Generar ID básico incremental basado en el último registro
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
        
        return response;
    }

    // --- MÉTODOS DE PERSISTENCIA EN ARCHIVO ---

    private void writeToFile(List<Person> people) {
        // FileWriter(filePath, false) sobreescribe el archivo con la lista actualizada
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Person p : people) {
                // Formato CSV simple: id,nombre,apellido,edad
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