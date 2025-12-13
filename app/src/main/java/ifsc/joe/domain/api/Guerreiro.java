package ifsc.joe.domain.api;

import ifsc.joe.domain.impl.Personagem;
import java.util.Collection;

public interface Guerreiro {

    void atacarArea(Collection<Personagem> alvos);
    default void atacar() {
    }
}
