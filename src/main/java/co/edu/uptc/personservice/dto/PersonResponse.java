package co.edu.uptc.personservice.dto;

import lombok.Data;

@Data
public class PersonResponse {
    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String message; // Opcional: "Persona registrada con éxito"
}