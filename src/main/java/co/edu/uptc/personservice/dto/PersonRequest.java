package co.edu.uptc.personservice.dto;

import lombok.Data;

@Data // Usando Lombok
public class PersonRequest {
    private String name;
    private String lastName;
    private int age;
}
