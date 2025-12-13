package ifsc.joe.domain.ui;

import ifsc.joe.config.Config;
import ifsc.joe.domain.enums.Direcao;
import ifsc.joe.domain.impl.Personagem;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.enums.TipoPersonagem;
import ifsc.joe.domain.impl.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    private int totalMortos = 0;

    private final Timer timer;

    public Tela() {
        setBackground(Color.WHITE);

        int padding = Config.getInt("tela.padding");
        setBorder(new EmptyBorder(padding, padding, padding, padding));

        personagens = new HashSet<>();

        timer = new Timer(16, e -> {
            atualizarEstados();
            repaint();
        });
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                Config.getInt("tela.largura"),
                Config.getInt("tela.altura")
        );
    }

    private void atualizarEstados() {
        for (Personagem p : personagens) {
            p.atualizarAnimacoes();
        }
        removerMortos();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (Personagem p : personagens) {
            p.atualizarAnimacoes();
            p.desenhar(g2, this);

            if (p instanceof Guerreiro && p.estaAtacando()) {
                desenharRangeAtaque(g2, p);
            }
        }
    }


    private void desenharRangeAtaque(Graphics2D g2, Personagem p) {
        g2.setColor(new Color(255, 0, 0, 80));

        int raio = p.getAlcance();
        int x = p.getPosX() - raio;
        int y = p.getPosY() - raio;

        g2.fillOval(x, y, raio * 2, raio * 2);
    }

    public void criarPersonagem(TipoPersonagem tipo, int x, int y) {
        Personagem p = switch (tipo) {
            case ALDEAO -> new Aldeao(x, y);
            case ARQUEIRO -> new Arqueiro(x, y);
            case CAVALEIRO -> new Cavaleiro(x, y);
        };

        personagens.add(p);
    }

    public void movimentarTodos(Direcao direcao) {
        for (Personagem p : personagens) {
            p.mover(direcao, getWidth(), getHeight());
        }
    }

    public void atacarTodos() {
        for (Personagem p : personagens) {

            p.iniciarAtaque();
            if (p instanceof Guerreiro guerreiro) {
                guerreiro.atacarArea(personagens);
            }
        }

        removerMortos();
        repaint();
    }

    private void removerMortos() {

        // inicia fade-out
        personagens.forEach(p -> {
            if (p.getVida() <= 0 && !p.isMorrendo()) {
                p.iniciarFadeOut();
            }
        });

        // remove após fade
        personagens.removeIf(p -> {
            if (p.getAlpha() <= 0f) {
                totalMortos++;
                System.out.println("Personagens mortos: " + totalMortos);
                return true;
            }
            return false;
        });
    }
}