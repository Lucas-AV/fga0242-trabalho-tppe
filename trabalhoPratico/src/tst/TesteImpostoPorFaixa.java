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
public class TesteImpostoPorFaixa {

    private IRPF irpf;
    private float salario;
    private float aliquotaEsperada;

    // Construtor parametrizado
    public TesteImpostoPorFaixa(float salario, float aliquotaEsperada) {
        this.salario = salario;
        this.aliquotaEsperada = aliquotaEsperada;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
        
        // Cadastro dos rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, salario);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // Teste de faixas de acordo com as tabelas do README
            {2259.20f, 0.0f},
            {2259.21f, 0.075f},
            {2826.66f, 0.15f},  
            {3751.06f, 0.225f}, 
            {4664.69f, 0.275f},
        });
    }

    @Test
    public void calculaTotalImpostos() {
        assertEquals(aliquotaEsperada, irpf.getPorcentagemFaixa(), 0.01f);
    }
}