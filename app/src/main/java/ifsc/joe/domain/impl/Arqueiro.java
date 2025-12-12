package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;

import java.util.List;

public class Arqueiro extends Personagem implements Guerreiro {

    private int flechas = 20;

    public Arqueiro(int posX, int posY) {
        super(posX, posY, 40, 1.0, 10);
        this.alcance = 120;
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
