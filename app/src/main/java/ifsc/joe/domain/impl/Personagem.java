package ifsc.joe.domain.impl;

import ifsc.joe.domain.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.util.Objects;
import java.util.Random;

public abstract class Personagem {

    protected int vida;
    protected int ataque;
    protected int velocidade;
    protected int alcance;
    protected double chanceEsquiva;
    protected int posX, posY;
    protected Image icone;
    protected boolean atacando = false;
    protected boolean morrendo = false;
    protected float alpha = 1.0f;
    private long fimAnimacaoAtaque = 0;
    private long tempoEsquiva = 0;

    private static final Random RANDOM = new Random();

    protected Personagem(
            int posX,
            int posY,
            int vida,
            int ataque,
            int velocidade,
            int alcance,
            double chanceEsquiva
    ) {
        this.posX = posX;
        this.posY = posY;
        this.vida = vida;
        this.ataque = ataque;
        this.velocidade = velocidade;
        this.alcance = alcance;
        this.chanceEsquiva = chanceEsquiva;
        this.icone = carregarImagem(getNomeImagem());
    }

    public int getVida() {
        return vida;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isMorrendo() {
        return morrendo;
    }

    public boolean estaAtacando() {
        return atacando;
    }

    public int getAlcance() {
        return alcance;
    }

    public int getPosX() {
        return posX + icone.getWidth(null) / 2;
    }

    public int getPosY() {
        return posY + icone.getHeight(null) / 2;
    }

    public void iniciarAtaque() {
        atacando = true;
        fimAnimacaoAtaque = System.currentTimeMillis() + 400;
    }

    public void sofrerDano(int dano) {
        if (tentouEsquivar()) {
            tempoEsquiva = System.currentTimeMillis();
            return;
        }
        vida = Math.max(0, vida - dano);
    }

    protected boolean tentouEsquivar() {
        return RANDOM.nextDouble() < chanceEsquiva;
    }

    public double calcularDistancia(Personagem outro) {
        return Math.hypot(outro.posX - posX, outro.posY - posY);
    }

    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        switch (direcao) {
            case CIMA -> posY -= velocidade;
            case BAIXO -> posY += velocidade;
            case ESQUERDA -> posX -= velocidade;
            case DIREITA -> posX += velocidade;
        }
        limitarDentroDaTela(maxLargura, maxAltura);
    }

    protected void limitarDentroDaTela(int maxLargura, int maxAltura) {
        posX = Math.min(Math.max(0, posX), maxLargura - icone.getWidth(null));
        posY = Math.min(Math.max(0, posY), maxAltura - icone.getHeight(null));
    }

    public void atualizarAnimacoes() {

        // fim ataque
        if (atacando && System.currentTimeMillis() > fimAnimacaoAtaque) {
            atacando = false;
        }

        if (atacando && System.currentTimeMillis() > fimAnimacaoAtaque) {
            atacando = false;
        }

        // fade-out morte
        if (morrendo && alpha > 0f) {
            alpha -= 0.03f;
            if (alpha < 0f) alpha = 0f;
        }
    }

    public void iniciarFadeOut() {
        morrendo = true;
    }

    public void desenhar(Graphics g, Component tela) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        );

        desenharImagem(g2, tela);
        desenharEfeitos(g2);

        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)
        );
    }

    protected void desenharImagem(Graphics2D g2, Component tela) {
        if (atacando) {
            g2.drawImage(
                    icone,
                    posX + icone.getWidth(null),
                    posY,
                    -icone.getWidth(null),
                    icone.getHeight(null),
                    tela
            );
        } else {
            g2.drawImage(icone, posX, posY, tela);
        }
    }

    protected void animarAtaque() {
        iniciarAtaque();
    }

    protected void desenharEfeitos(Graphics2D g2) {
        if (System.currentTimeMillis() - tempoEsquiva < 600) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("ESQUIVOU!", posX, posY - 10);
        }
    }

    protected abstract String getNomeImagem();

    protected Image carregarImagem(String imagem) {
        return new ImageIcon(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource(imagem + ".png")
                )
        ).getImage();
    }
}
