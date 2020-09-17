package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

@Data
public class StringDTO {

    private String value;

    public StringDTO(String value) {
        this.value = value;
    }
}
