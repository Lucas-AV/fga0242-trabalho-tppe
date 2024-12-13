package tst;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import app.IRPF;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TesteBaseDeCalculo {

    private IRPF irpf;
    private float salario;
    private float aluguel;
    private float bolsaEstudos;
    private float contribuicaoPrevidenciariaPrivada;
    private float contribuicaoPrevidenciariaOficial;
    private float pensaoAlimenticia;
    private float resultadoEsperado;

    // Construtor parametrizado
    public TesteBaseDeCalculo(float salario, float aluguel, float bolsaEstudos, float contribuicaoPrevidenciariaPrivada, 
                                float contribuicaoPrevidenciariaOficial, float pensaoAlimenticia, float resultadoEsperado) {
        this.salario = salario;
        this.aluguel = aluguel;
        this.bolsaEstudos = bolsaEstudos;
        this.contribuicaoPrevidenciariaPrivada = contribuicaoPrevidenciariaPrivada;
        this.contribuicaoPrevidenciariaOficial = contribuicaoPrevidenciariaOficial;
        this.pensaoAlimenticia = pensaoAlimenticia;
        this.resultadoEsperado = resultadoEsperado;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
        
        // Cadastro dos rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, salario);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, aluguel);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, bolsaEstudos);
        
        // Cadastro do dependente Filho
        irpf.cadastrarDependente("filho", "filho");
        irpf.cadastrarPensaoAlimenticia("filho", pensaoAlimenticia);
        
        // Cadastro das deduções
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária privada", contribuicaoPrevidenciariaPrivada);
        irpf.cadastrarContribuicaoPrevidenciaria(contribuicaoPrevidenciariaOficial);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // Caso 0: Rendimentos e deduções com valores conhecidos
            {8000f, 2000f, 1500f, 1000f, 500f, 1500f, 6810.41f},

            // Caso 1: Teste com salário maior e deduções altas
            {15000f, 3000f, 1000f, 2000f, 1000f, 2000f, 12810.41f},

            // Caso 2: Teste com salário e aluguel baixos e alta pensão alimentícia
            {3000f, 1000f, 0f, 500f, 1000f, 3000f, 0f},

            // Caso 3: Teste sem rendimentos tributáveis
            {0f, 0f, 0f, 0f, 0f, 0f, 0f}
        });
    }

    @Test
    public void testBaseDeCalculo() {
        assertEquals(resultadoEsperado, irpf.calcularBaseDeCalculo(), 0.01f);
    }
}
