package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;

import java.util.List;

public class Cavaleiro extends Personagem implements Guerreiro {

    public Cavaleiro(int posX, int posY) {
        super(posX, posY, 75, 2.0, 15);
        this.alcance = 60;
        this.chanceEsquiva = 0.15;
    }

    @Override
    protected String getNomeImagem() {
        return "cavaleiro";
    }

    @Override
    public void atacarArea(List<Personagem> alvos) {
        for (Personagem alvo : alvos) {
            if (alvo == this || alvo.getVida() <= 0) continue; // para eles n se suicidarem
            double distancia = this.calcularDistancia(alvo);
            if (distancia <= 60) {
                alvo.sofrerDano(this.ataque);
            }
        }
    }
}
