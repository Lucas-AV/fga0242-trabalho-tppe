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
    private String nomeRendimento1;
    private float valorRendimento1;
    private boolean tributavel1;

    private String nomeRendimento2;
    private float valorRendimento2;
    private boolean tributavel2;

    private String nomeRendimento3;
    private float valorRendimento3;
    private boolean tributavel3;

    private String dependenteNome;
    private String dependenteParentesco;

    private float valorContribuicaoPrevidenciaria;
    private float valorPensaoAlimenticia;

    private float esperadoBaseDeCalculo;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // {nomeRendimento1, valorRendimento1, tributavel1, nomeRendimento2, valorRendimento2, tributavel2, nomeRendimento3, valorRendimento3, tributavel3, dependenteNome, dependenteParentesco, valorContribuicaoPrevidenciaria, valorPensaoAlimenticia, esperadoBaseDeCalculo}
            {"Salário", 8000, true, "Aluguel", 2000, true, "Bolsa de estudos", 1500, false, "filho", "filho", 1000, 1500, 6810.41f},
            // Adicionar outros casos de teste conforme necessário
        });
    }

    public TesteBaseDeCalculo(String nomeRendimento1, float valorRendimento1, boolean tributavel1,
                               String nomeRendimento2, float valorRendimento2, boolean tributavel2,
                               String nomeRendimento3, float valorRendimento3, boolean tributavel3,
                               String dependenteNome, String dependenteParentesco,
                               float valorContribuicaoPrevidenciaria, float valorPensaoAlimenticia,
                               float esperadoBaseDeCalculo) {
        this.nomeRendimento1 = nomeRendimento1;
        this.valorRendimento1 = valorRendimento1;
        this.tributavel1 = tributavel1;

        this.nomeRendimento2 = nomeRendimento2;
        this.valorRendimento2 = valorRendimento2;
        this.tributavel2 = tributavel2;

        this.nomeRendimento3 = nomeRendimento3;
        this.valorRendimento3 = valorRendimento3;
        this.tributavel3 = tributavel3;

        this.dependenteNome = dependenteNome;
        this.dependenteParentesco = dependenteParentesco;

        this.valorContribuicaoPrevidenciaria = valorContribuicaoPrevidenciaria;
        this.valorPensaoAlimenticia = valorPensaoAlimenticia;

        this.esperadoBaseDeCalculo = esperadoBaseDeCalculo;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
        
        // Cadastro dos rendimentos
        irpf.criarRendimento(nomeRendimento1, tributavel1, valorRendimento1);
        irpf.criarRendimento(nomeRendimento2, tributavel2, valorRendimento2);
        irpf.criarRendimento(nomeRendimento3, tributavel3, valorRendimento3);
        
        // Cadastro do dependente
        irpf.cadastrarDependente(dependenteNome, dependenteParentesco);
        irpf.cadastrarPensaoAlimenticia(dependenteNome, valorPensaoAlimenticia); 
        
        // Cadastro das deduções
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária privada", valorContribuicaoPrevidenciaria);
        irpf.cadastrarContribuicaoPrevidenciaria(valorContribuicaoPrevidenciaria);
    }

    @Test
    public void testBaseDeCalculo() {
        assertEquals(esperadoBaseDeCalculo, irpf.calcularBaseDeCalculo(), 0.01f);
    }
}

