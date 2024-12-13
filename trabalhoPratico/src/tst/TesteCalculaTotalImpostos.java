package tst;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import app.IRPF;

@RunWith(Parameterized.class)
public class TesteCalculaTotalImpostos {
    IRPF irpf;

    // Parâmetros para o teste
    private float expectedImpostos;
    private float salario;
    private float aluguel;
    private float bolsa;
    private float pensao;
    private float deducaoIntegral;
    private float contribuicao;

    // Construtor para os parâmetros
    public TesteCalculaTotalImpostos(float expectedImpostos, float salario, float aluguel, float bolsa, float pensao, float deducaoIntegral, float contribuicao) {
        this.expectedImpostos = expectedImpostos;
        this.salario = salario;
        this.aluguel = aluguel;
        this.bolsa = bolsa;
        this.pensao = pensao;
        this.deducaoIntegral = deducaoIntegral;
        this.contribuicao = contribuicao;
    }

    // Método que fornece os dados para os testes
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // expectedImpostos, salario, aluguel, bolsa, pensao, deducaoIntegral, contribuicao
            {987.88f, 8000, 2000, 1500, 1500, 1000, 500}, // Cenário 1: Valores originais
            {850.00f, 7000, 2000, 1500, 1200, 900, 400},   // Cenário 2: Valores alterados
            {1100.50f, 9000, 2500, 0, 2000, 1500, 600}     // Cenário 3: Outro conjunto de valores
        });
    }

    @Before
    public void setup() {
        irpf = new IRPF();

        // Cadastro dos rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, salario);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, aluguel);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, bolsa);

        // Cadastro do dependente Filho
        irpf.cadastrarDependente("filho", "filho");
        irpf.cadastrarPensaoAlimenticia("filho", pensao);

        // Cadastro das deduções
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária privada", deducaoIntegral);
        irpf.cadastrarContribuicaoPrevidenciaria(contribuicao);
    }

    @Test
    public void testeCalculaTotalImpostos() {
        assertEquals(expectedImpostos, irpf.calculaTotalImpostos(), 0.01f);
    }
}
