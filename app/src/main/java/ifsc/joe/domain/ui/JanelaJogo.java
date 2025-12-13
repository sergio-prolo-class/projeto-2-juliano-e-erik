package ifsc.joe.domain.ui;

import javax.swing.*;

public class JanelaJogo {

    private static final String TITULO = "Java of Empires";
    private final JFrame frame;
    private final PainelControles painelControles;

    public JanelaJogo() {
        this.frame = new JFrame(TITULO);
        this.painelControles = new PainelControles();

        this.configurarJanela();
    }

    private void configurarJanela() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(painelControles.getPainelPrincipal());
        frame.pack(); // usa o getPreferredSize da tela
        frame.setLocationRelativeTo(null);
    }

    public void exibir() {
        frame.setVisible(true);
    }
}