package ifsc.joe.domain.impl;

public class Arqueiro extends Personagem {

    private int flechas;

    public Arqueiro(int posX, int posY, int vida) {
        super(posX, posY, 35, 1.0, 2);
    }


    @Override
    protected String getNomeImagem() {
        return "arqueiro"; // arqueiro.png
    }
}
