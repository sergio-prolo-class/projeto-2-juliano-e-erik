package ifsc.joe.domain.impl;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {

    protected int vida;
    protected int ataque;
    protected double velocidade;
    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;

    public Personagem(int posX, int posY, int vida, double velocidade, int ataque) {
        this.icone = carregarImagem(getNomeImagem());
        this.posX = posX;
        this.posY = posY;
        this.atacando = false;
        this.ataque = ataque;
        this.vida = vida;
        this.velocidade = velocidade;
    }


    protected abstract String getNomeImagem();

    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        switch (direcao) {
            case CIMA     -> this.posY -= 10;
            case BAIXO    -> this.posY += 10;
            case ESQUERDA -> this.posX -= 10;
            case DIREITA  -> this.posX += 10;
        }

        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void atacar() {
        this.atacando = !this.atacando;
    }

    public void desenhar(Graphics g, Component tela) {
        g.drawImage(this.icone, this.posX, this.posY, tela);
    }

    protected Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + imagem + ".png")
        )).getImage();
    }
}
