package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.config.Config;

import java.util.List;

public class Aldeao extends Personagem implements Guerreiro {

    public Aldeao(int posX, int posY) {
        super(
                posX,
                posY,
                Config.getInt("aldeao.vida"),
                Config.getInt("aldeao.velocidade"),
                Config.getInt("aldeao.ataque"),
                Config.getInt("aldeao.alcance"),
                Config.getDouble("aldeao.chanceEsquiva")
        );
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
