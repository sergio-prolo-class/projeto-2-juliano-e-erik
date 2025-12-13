package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.config.Config;

import java.util.List;
import java.util.Collection;

public class Arqueiro extends Personagem implements Guerreiro {

    private int flechas;

    public Arqueiro(int posX, int posY) {
        super(
                posX,
                posY,
                Config.getInt("arqueiro.vida"),
                Config.getInt("arqueiro.velocidade"),
                Config.getInt("arqueiro.ataque"),
                Config.getInt("arqueiro.alcance"),
                Config.getDouble("arqueiro.chanceEsquiva")
        );
        this.flechas = Config.getInt("arqueiro.flechas");
    }

    @Override
    protected String getNomeImagem() {
        return "arqueiro";
    }

    @Override
    public void atacarArea(Collection<Personagem> alvos) {
        if (flechas <= 0) return;

        for (Personagem alvo : alvos) {
            if (alvo == this || alvo.getVida() <= 0) continue;

            if (calcularDistancia(alvo) <= alcance) {
                iniciarAtaque();
                animarAtaque();
                alvo.sofrerDano(ataque);
                flechas--;
                if (flechas <= 0) break;
            }
        }
    }
}