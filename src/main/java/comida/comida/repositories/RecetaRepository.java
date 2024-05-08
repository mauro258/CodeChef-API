package comida.comida.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import comida.comida.entities.RecetaEntity;

public interface RecetaRepository extends JpaRepository<RecetaEntity, Long> {
    
}
