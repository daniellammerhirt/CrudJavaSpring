package crudjavaspring.repository;

import crudjavaspring.model.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {

}
