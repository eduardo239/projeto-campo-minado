package modelo;

import excecao.ExplosaoExcpetion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private final List<Campo> campos = new ArrayList<>();
    private int linhas;
    private int colunas;
    private int minas;

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        // init
        gerarCampos();
        associarVizinhos();
        sortearMinhas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
        } catch (ExplosaoExcpetion e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }

    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                campos.add(new Campo(l, c));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinhas() {
        long minasArmadas = 0;

        Predicate<Campo> minado = c -> c.isMinado();

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinhas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("¤ - ");
        for (int c = 0; c < colunas; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");
        for (int x = 0; x < colunas * 2; x++) {
            sb.append(" -");
        }

        sb.append("\n");

        int i = 0;
        for (int l = 0; l < linhas; l++) {
            sb.append(l);
            sb.append(" | ");
            for (int c = 0; c < colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("=\n");
        }

        return sb.toString();
    }

}
