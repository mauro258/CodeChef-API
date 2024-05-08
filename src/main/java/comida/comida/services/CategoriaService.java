package comida.comida.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comida.comida.entities.CategoriaEntity;
import comida.comida.repositories.CategoriaRepository;




@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public void saveCategoria(CategoriaEntity categoria){
        categoriaRepository.save(categoria);
    }
}
