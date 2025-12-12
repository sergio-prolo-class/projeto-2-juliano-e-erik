package ifsc.joe.domain.impl;

import ifsc.joe.domain.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public abstract class Personagem {

    protected int vida;
    protected int ataque;
    protected int velocidade;
    protected int alcance;
    protected double chanceEsquiva;
    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;
    protected float alpha = 1.0f;
    protected boolean morrendo = false;
    private long tempoEsquiva = 0;


    public Personagem(int posX, int posY, int vida, int velocidade, int ataque, int alcance, double chanceEsquiva) {
        this.icone = carregarImagem(getNomeImagem());
        this.posX = posX;
        this.posY = posY;
        this.vida = vida;
        this.velocidade = velocidade;
        this.ataque = ataque;
        this.alcance = alcance;
        this.chanceEsquiva = chanceEsquiva;
    }

    public int getVida() {
        return vida;
    }
    public int getAtaque() {
        return ataque;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public boolean isMorrendo() {
        return morrendo;
    }

    public float getAlpha() {
        return alpha;
    }

    public void atacar() {
        this.atacando = !this.atacando;
    }

    public void setMorrendo(boolean morrendo) {
        this.morrendo = morrendo;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void sofrerDano(int dano) {
        // Random para decidir esquiva
        if (Math.random() < chanceEsquiva) {
            exibirEfeitoEsquiva();
            return;
        }

        this.vida = Math.max(0, this.vida - dano);
    }

    protected void exibirEfeitoEsquiva() {
        tempoEsquiva = System.currentTimeMillis();
    }

    //metodo para o personagem morto sair da tela

    public void iniciarFadeOut() {
        morrendo = true;

        new Thread(() -> {
            try {
                while (alpha > 0) {
                    alpha -= 0.05f;
                    Thread.sleep(30);
                }
                vida = 0;
            } catch (InterruptedException ignored) {}
        }).start();
    }

    public double calcularDistancia(Personagem outro) {
        return Math.hypot(outro.posX - this.posX, outro.posY - this.posY);
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

    public void desenhar(Graphics g, Component tela) {
        Graphics2D g2 = (Graphics2D) g;

        // aplica o desaparecer
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (atacando) {
            g2.drawImage(
                    this.icone,
                    this.posX + icone.getWidth(null),
                    this.posY,
                    -icone.getWidth(null),
                    icone.getHeight(null),
                    tela
            );
        } else {
            g2.drawImage(this.icone, this.posX, this.posY, tela);
        }

        if (System.currentTimeMillis() - tempoEsquiva < 600) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 14));

            g2.drawString(
                    "ESQUIVOU!",
                    this.posX,
                    this.posY - 10
            );
        }

        if (atacando) {
            g2.setColor(new Color(255, 0, 0, 60));

            int centerX = posX + icone.getWidth(null) / 2;
            int centerY = posY + icone.getHeight(null) / 2;

            g2.fillOval(
                    centerX - alcance,
                    centerY - alcance,
                    alcance * 2,
                    alcance * 2
            );
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    protected Image carregarImagem(String imagem) {
        return new ImageIcon(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("./" + imagem + ".png")
                )
        ).getImage();
    }
}