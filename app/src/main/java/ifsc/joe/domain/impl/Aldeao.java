package ifsc.joe.domain.impl;

public class Aldeao extends Personagem {


    public Aldeao(int posX, int posY, int vida, double velocidade, int ataque) {
        super(posX, posY, 25, 0.8, 1);
    }

    @Override
    protected String getNomeImagem() {
        return "aldeao";
    }
}
