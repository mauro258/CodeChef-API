package comida.comida.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comida.comida.entities.RecetaEntity;
import comida.comida.repositories.RecetaRepository;


@Service
public class RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    public void saveReceta(RecetaEntity receta){
        recetaRepository.save(receta);
    }
}
