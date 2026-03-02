package co.edu.uptc.personservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWrapper <T> {
    private String serverIp;
    private String instance;
    private T data;
}
