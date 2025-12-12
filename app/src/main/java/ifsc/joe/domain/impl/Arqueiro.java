package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.config.Config;

import java.util.List;

public class Arqueiro extends Personagem implements Guerreiro {

    private int flechas = 20;

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
    }

    @Override
    protected String getNomeImagem() {
        return "arqueiro";
    }

    @Override
    public void atacarArea(List<Personagem> alvos) {
        if (flechas <= 0) return;

        for (Personagem alvo : alvos) {
            if (alvo == this || alvo.getVida() <= 0) continue; // para eles n se suicidarem
            double distancia = this.calcularDistancia(alvo);
            if (distancia <= 120) {
                alvo.sofrerDano(this.ataque);
                flechas--;
            }
        }
    }
}
