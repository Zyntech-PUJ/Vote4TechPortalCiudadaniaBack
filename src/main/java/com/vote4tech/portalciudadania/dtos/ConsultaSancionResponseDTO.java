package com.vote4tech.portalciudadania.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSancionResponseDTO {
    private boolean found;
    private String mensaje;
}
