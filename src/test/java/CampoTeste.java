import excecao.ExplosaoExcpetion;
import modelo.Campo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3,3);
    }

    @Test
    @DisplayName("Testa se é vizinho da esquerda")
    void testeVizinhoDistancia1Esquerda(){
        Campo vizinho = new Campo(3,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Testa se é vizinho da direita")
    void testeVizinhoDistancia1Direita(){
        Campo vizinho = new Campo(3,4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Se é vizinho de cima")
    void testeVizinhoRealDistancia1Cima(){
        Campo vizinho = new Campo(2,3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Se é vizinho de baixo")
    void testeVizinhoDistancia1Baixo(){
        Campo vizinho = new Campo(4,3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Teste de distância diagonal")
    void testeVizinhoDistancia2(){
        Campo vizinho = new Campo(2,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Teste de não vizinho na diagonal")
    void testeNaoVizinhoDistancia2(){
        Campo vizinho = new Campo(1,1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Alternar marcacao")
    void testeAlternarMarcacao() {
        assertFalse(campo.isMarcado());
    }

    @Test
    @DisplayName("Alternar marcacao")
    void testeValorPadraoAtributoMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    @DisplayName("Alternar marcacao 2 vezes")
    void testeValorPadraoAtributoMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    @DisplayName("Teste se o campo abre")
    void testeAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    @DisplayName("Alternar marcacao 2 vezes")
    void testeAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    @DisplayName("Alternar marcacao 2 vezes")
    void testeAbrirMinadoMarcado() {
        campo.minar();
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }
    @Test
    @DisplayName("Testa se chama a exceção")
    void testeAbrirMinadoNaoMarcado() {
        campo.minar();

        assertThrows(ExplosaoExcpetion.class, () -> {
            // se abrir chamar throw
            campo.abrir();
        });
    }

    @Test
    @DisplayName("Teste de abrir com vizinhos")
    void testeAbrirComVizinhos1() {
        Campo campo11 = new Campo(1,1);
        Campo campo22 = new Campo(2,2);

        campo22.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo22);

        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    @DisplayName("Teste de abrir com vizinhos")
    void testeAbrirComVizinhos2() {
        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,2);
        campo12.minar();

        Campo campo22 = new Campo(2,2);

        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);
        campo.adicionarVizinho(campo22);

        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }
}
