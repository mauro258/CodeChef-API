package comida.comida.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import comida.comida.entities.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    
}
