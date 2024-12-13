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
public class TesteAliquotaEfetiva {

    private IRPF irpf;
    private float rendimentoSalario;
    private float rendimentoAluguel;
    private float contribuicaoPrevidenciaria;
    private float valorDependente;
    private float valorPensao;
    private float resultadoEsperado;

    public TesteAliquotaEfetiva(float rendimentoSalario, float rendimentoAluguel, 
                                float contribuicaoPrevidenciaria, float valorDependente, 
                                float valorPensao, float resultadoEsperado) {
        this.rendimentoSalario = rendimentoSalario;
        this.rendimentoAluguel = rendimentoAluguel;
        this.contribuicaoPrevidenciaria = contribuicaoPrevidenciaria;
        this.valorDependente = valorDependente;
        this.valorPensao = valorPensao;
        this.resultadoEsperado = resultadoEsperado;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parametros() {
        return Arrays.asList(new Object[][] {
            { 8000, 2000, 1000, 1, 1500, 8.39f },
            { 0, 0, 0, 0, 0, 0f },
            { 0, 3000, 0, 0, 0, 2.28f },
            { 5000, 0, 3000, 0, 0, 0f },
            { 50000, 20000, 10000, 0, 0, 18.36f },
            { 15000, 0, 2000, 3, 0, 13.15f }
        });
    }

    @Test
    public void testCalcularAliquotaEfetiva() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, rendimentoSalario);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, rendimentoAluguel);
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária", contribuicaoPrevidenciaria);
        irpf.cadastrarContribuicaoPrevidenciaria(contribuicaoPrevidenciaria);
        
        for (int i = 0; i < valorDependente; i++) {
            irpf.cadastrarDependente("Fulano " + (i + 1), "filho");
            irpf.cadastrarPensaoAlimenticia("Fulano " + (i + 1), valorPensao);
        }


        assertEquals(resultadoEsperado, irpf.aliquotaEfetiva(), 0.01f);
    }
}
