package com.vote4tech.portalciudadania.enums;

public enum TipoJurado {
  DOMICILIO,
  URNA;

  public static TipoJurado from(String value) {
    if (value == null || value.isBlank()) {
      return URNA; // default
    }

    try {
      return TipoJurado.valueOf(value.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      return URNA; // fallback si no reconoce
    }
  }
}
