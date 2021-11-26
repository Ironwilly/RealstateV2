package edu.salesianos.triana.RealStateV2.services;

import edu.salesianos.triana.RealStateV2.model.Inmobiliaria;
import edu.salesianos.triana.RealStateV2.repositorios.InmobiliariaRepository;
import edu.salesianos.triana.RealStateV2.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria,Long, InmobiliariaRepository> {
}
