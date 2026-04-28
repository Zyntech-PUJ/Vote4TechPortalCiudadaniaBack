package com.vote4tech.portalciudadania.service;

import com.vote4tech.portalciudadania.entity.*;
import com.vote4tech.portalciudadania.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ElectoralService {

    private final CiudadanoRepository ciudadanoRepository;
    private final CensoElectoralSnapshotRepository censoRepository;
    private final JuradoRepository juradoRepository;

    public ElectoralService(CiudadanoRepository ciudadanoRepository, CensoElectoralSnapshotRepository censoRepository, JuradoRepository juradoRepository) {
        this.ciudadanoRepository = ciudadanoRepository;
        this.censoRepository = censoRepository;
        this.juradoRepository = juradoRepository;
    }

    public Map<String, Object> consultarSancion(String cedula) {
        Map<String, Object> result = new HashMap<>();
        Optional<Ciudadano> ciudadanoOpt = ciudadanoRepository.findByNumeroDoc(cedula);
        
        if (ciudadanoOpt.isEmpty()) {
            result.put("found", false);
            result.put("mensaje", "No se encontró el ciudadano con el documento ingresado.");
            return result;
        }
        
        Ciudadano c = ciudadanoOpt.get();
        if ("SUSPENDIDO".equalsIgnoreCase(c.getDerechosPoliticosEstado())) {
            result.put("found", true);
            result.put("mensaje", "El ciudadano registra una sanción. Por favor acerquese a un punto de la registraduría para mayor informacion.");
        } else {
            result.put("found", false);
            result.put("mensaje", "No se encontraron sanciones para el documento ingresado.");
        }
        return result;
    }

    public Map<String, Object> consultarLugarVotacion(String cedula, String eleccion) {
        Map<String, Object> result = new HashMap<>();
        Optional<CensoElectoralSnapshot> censoOpt = censoRepository.findByCiudadanoNumeroDocAndEleccionNombre(cedula, eleccion);
        
        if (censoOpt.isEmpty()) {
            result.put("found", false);
            return result;
        }
        
        CensoElectoralSnapshot censo = censoOpt.get();
        PuestoVotacion puesto = censo.getPuestoAsignado();
        
        result.put("found", true);
        if (puesto != null && puesto.getCentro() != null) {
            result.put("puesto", puesto.getCentro().getNombre());
            result.put("direccion", puesto.getCentro().getDireccion());
            if (puesto.getCentro().getMunicipio() != null) {
                result.put("municipio", puesto.getCentro().getMunicipio().getNombre());
            }
            result.put("mesa", "Por definir");
        }
        return result;
    }

    public Map<String, Object> consultarJurado(String cedula) {
        Map<String, Object> result = new HashMap<>();
        Optional<Jurado> juradoOpt = juradoRepository.findByCiudadanoNumeroDoc(cedula);
        
        if (juradoOpt.isEmpty()) {
            result.put("found", false);
            result.put("mensaje", "No se encontró designación de jurado para el documento ingresado.");
            return result;
        }
        
        Jurado jurado = juradoOpt.get();
        String mesaStr = jurado.getMesa() != null ? jurado.getMesa().getNumeroMesa().toString() : "N/A";
        String puestoStr = (jurado.getMesa() != null && jurado.getMesa().getPuesto() != null && jurado.getMesa().getPuesto().getCentro() != null) 
                            ? jurado.getMesa().getPuesto().getCentro().getNombre() : "N/A";

        result.put("found", true);
        result.put("mensaje", "Fuiste designado como jurado en el puesto " + puestoStr + ", mesa " + mesaStr + ".");
        return result;
    }
}
