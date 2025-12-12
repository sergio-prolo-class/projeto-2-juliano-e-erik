package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;

import java.util.List;

public class Aldeao extends Personagem implements Guerreiro {

    public Aldeao(int posX, int posY) {
        super(posX, posY, 25, 0.8, 5);
        this.alcance = 40;
        this.chanceEsquiva = 0.10;
    }

    @Override
    protected String getNomeImagem() {
        return "aldeao";
    }

    @Override
    public void atacarArea(List<Personagem> alvos) {
        for (Personagem alvo : alvos) {
            if (alvo == this || alvo.getVida() <= 0) continue; // para eles n se suicidarem
            double distancia = this.calcularDistancia(alvo);
            if (distancia <= 40) {
                alvo.sofrerDano(this.ataque);
            }
        }
    }
}
