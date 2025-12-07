package ifsc.joe.domain.impl;

public class Cavaleiro extends Personagem {


    public Cavaleiro(int posX, int posY, int vida, double velocidade, int ataque) {
        super(posX, posY, 50, 2.0, 3);
    }

    @Override
    protected String getNomeImagem() {
        return "cavaleiro"; // cavaleiro.png
    }
}
