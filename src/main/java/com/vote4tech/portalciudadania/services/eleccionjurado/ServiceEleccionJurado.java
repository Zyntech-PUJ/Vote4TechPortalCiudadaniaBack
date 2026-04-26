package com.vote4tech.portalciudadania.services.eleccionjurado;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote4tech.portalciudadania.dtos.dashboard.DashboardEleccionDTO;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.CreateEleccionJuradoDTO;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.MapperEleccionJurado;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.ResponseEleccionJuradoDTO;
import com.vote4tech.portalciudadania.entities.Ciudadano;
import com.vote4tech.portalciudadania.entities.Eleccion;
import com.vote4tech.portalciudadania.entities.EleccionJurado;
import com.vote4tech.portalciudadania.enums.EstadoEleccionJurado;
import com.vote4tech.portalciudadania.enums.TipoJurado;
import com.vote4tech.portalciudadania.exceptions.BusinessException;
import com.vote4tech.portalciudadania.exceptions.ResourceNotFoundException;
import com.vote4tech.portalciudadania.repositories.RepositoryCiudadano;
import com.vote4tech.portalciudadania.repositories.RepositoryEleccion;
import com.vote4tech.portalciudadania.repositories.RepositoryEleccionJurado;

@Service
public class ServiceEleccionJurado implements IServiceEleccionJurado {

  @Autowired
  RepositoryEleccionJurado repositoryEleccionJurado;

  @Autowired
  RepositoryEleccion repositoryEleccion;

  @Autowired
  RepositoryCiudadano repositoryCiudadano;

  @Autowired
  MapperEleccionJurado mapperEleccionJurado;

  @Override
  public ResponseEleccionJuradoDTO findByCedula(String cedula) {
    EleccionJurado asignacion = repositoryEleccionJurado
        .findFirstByCiudadano_CedulaOrderByIdAsignacionJuradoAsc(cedula)
        .orElseThrow(() -> new ResourceNotFoundException(
            "No existe asignacion de jurado para la cedula " + cedula + "."));
    return mapperEleccionJurado.toResponseDTO(asignacion);
  }

  @Override
  public ResponseEleccionJuradoDTO addEleccionJurado(Long idEleccion, CreateEleccionJuradoDTO eleccionJuradoDTO) {
    Eleccion eleccion = repositoryEleccion.findById(idEleccion)
        .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe."));

    if (eleccionJuradoDTO.getNumeroMesa() == null || eleccionJuradoDTO.getNumeroMesa() < 1
        || eleccionJuradoDTO.getNumeroMesa() > 32) {
      throw new BusinessException("El numero de mesa debe estar entre 1 y 32.");
    }

    Ciudadano ciudadano = repositoryCiudadano.findByCedula(eleccionJuradoDTO.getCedulaCiudadano())
        .orElseThrow(() -> new ResourceNotFoundException(
            "No existe un ciudadano con cedula " + eleccionJuradoDTO.getCedulaCiudadano() + "."));

    if (repositoryEleccionJurado.existsByEleccion_IdEleccionAndCiudadano_Cedula(idEleccion, ciudadano.getCedula())) {
      throw new BusinessException(
          "El ciudadano con cedula " + ciudadano.getCedula() + " ya es jurado en esta eleccion.");
    }

    EleccionJurado eleccionJurado = mapperEleccionJurado.toEntity(eleccionJuradoDTO, ciudadano, eleccion);
    eleccionJurado.setEstado(EstadoEleccionJurado.PENDIENTE);

    return mapperEleccionJurado.toResponseDTO(repositoryEleccionJurado.save(eleccionJurado));
  }

  @Override
  public List<ResponseEleccionJuradoDTO> generarSorteo(Long idEleccion) {
    Eleccion eleccion = repositoryEleccion.findById(idEleccion)
        .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe."));

    if (repositoryEleccionJurado.existsByEleccion_IdEleccion(idEleccion)) {
      throw new BusinessException("La eleccion con id " + idEleccion + " ya tiene jurados sorteados.");
    }

    List<Ciudadano> ciudadanos = repositoryCiudadano.findAll();
    if (ciudadanos.isEmpty()) {
      throw new BusinessException("No hay ciudadanos registrados para realizar el sorteo.");
    }

    List<EleccionJurado> asignaciones = new ArrayList<>();
    for (Ciudadano ciudadano : ciudadanos) {
      EleccionJurado asignacion = new EleccionJurado();

      int aleatorio = ThreadLocalRandom.current().nextInt(0, 101); // genera entre 0 y 100
      TipoJurado tipo = aleatorio <= 49 ? TipoJurado.URNA : TipoJurado.DOMICILIO;

      asignacion.setCiudadano(ciudadano);
      asignacion.setEleccion(eleccion);
      asignacion.setTipoJurado(tipo);
      asignacion.setNumeroMesa(ThreadLocalRandom.current().nextInt(1, 33));
      asignacion.setFechaCapacitacion(eleccion.getFechaInicio().minusDays(10));
      asignacion.setEstado(EstadoEleccionJurado.PENDIENTE);
      asignaciones.add(asignacion);
    }

    return mapperEleccionJurado.toResponseDTOs(repositoryEleccionJurado.saveAll(asignaciones));
  }

  @Override
  public DashboardEleccionDTO getDashboard(Long idEleccion) {
    Eleccion eleccion = repositoryEleccion.findById(idEleccion)
        .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe."));

    long capacitados = repositoryEleccionJurado.countByEleccionAndEstado(idEleccion, EstadoEleccionJurado.CAPACITADO);
    long pendientes = repositoryEleccionJurado.countByEleccionAndEstado(idEleccion, EstadoEleccionJurado.PENDIENTE);
    long noPresentados = repositoryEleccionJurado.countByEleccionAndEstado(idEleccion,
        EstadoEleccionJurado.NO_PRESENTADO);

    DashboardEleccionDTO dto = new DashboardEleccionDTO();
    dto.setIdEleccion(eleccion.getIdEleccion());
    dto.setNombreEleccion(eleccion.getNombre());
    dto.setTotalJurados(capacitados + pendientes + noPresentados);
    dto.setCapacitados(capacitados);
    dto.setPendientes(pendientes);
    dto.setNoPresentados(noPresentados);

    return dto;
  }

}
