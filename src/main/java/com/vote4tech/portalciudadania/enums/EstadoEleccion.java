package com.vote4tech.portalciudadania.enums;

/**
  * Ciclo de vida de una Elección.
  *
  * CONFIGURACION  => la elección se puede editar libremente.
  * LANZADA        => la elección es INMUTABLE; no se permite ninguna modificación.
  * EN_CURSO       => jornada electoral activa.
  * FINALIZADA     => proceso cerrado; resultados consolidados.
*/
public enum EstadoEleccion {
  CONFIGURACION,
  LANZADA,
  EN_CURSO,
  FINALIZADA
}
