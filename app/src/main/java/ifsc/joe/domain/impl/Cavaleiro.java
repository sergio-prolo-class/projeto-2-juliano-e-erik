package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.config.Config;

import java.util.List;
import java.util.Collection;

public class Cavaleiro extends Personagem implements Guerreiro {

    public Cavaleiro(int posX, int posY) {
        super(
                posX,
                posY,
                Config.getInt("cavaleiro.vida"),
                Config.getInt("cavaleiro.velocidade"),
                Config.getInt("cavaleiro.ataque"),
                Config.getInt("cavaleiro.alcance"),
                Config.getDouble("cavaleiro.chanceEsquiva")
        );
    }

    @Override
    protected String getNomeImagem() {
        return "cavaleiro";
    }

    @Override
    public void atacarArea(Collection<Personagem> alvos) {
        for (Personagem alvo : alvos) {
            if (alvo == this || alvo.getVida() <= 0) continue;

            if (calcularDistancia(alvo) <= alcance) {
                iniciarAtaque();
                animarAtaque();
                alvo.sofrerDano(ataque);
            }
        }
    }
}
