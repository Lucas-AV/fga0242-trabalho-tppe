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
    private float impostoEsperado;

    // Construtor parametrizado
    public TesteImpostoPorFaixa(float salario, float impostoEsperado) {
        this.salario = salario;
        this.impostoEsperado = impostoEsperado;
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
            // Cenários de rendimento com imposto esperado
            {2259.49f, 2259.49f},  // Caso com salário de 2259.49 e imposto calculado esperado
            {4000.00f, 4000.0f},        // Caso com salário que não gera imposto
            {1000.00f, 1000.00f}, // Caso com salário mais alto e imposto gerado
            {50000.00f, 50000.00f} // Caso com salário ainda mais alto e imposto esperado
        });
    }

    @Test
    public void calculaTotalImpostos() {
        assertEquals(impostoEsperado, irpf.calcularBaseDeCalculo(), 0.01f);
    }
}