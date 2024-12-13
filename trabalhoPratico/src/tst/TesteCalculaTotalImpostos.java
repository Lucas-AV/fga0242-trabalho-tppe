package tst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteCalculaTotalImpostos {
    IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
        
        // Cadastro dos rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, 2000);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, 1500);
        
        // Cadastro do dependente Filho
        irpf.cadastrarDependente("filho", "filho");
        irpf.cadastrarPensaoAlimenticia("filho", 1500); 
        
        // Cadastro das deduções
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária privada", 1000);
        irpf.cadastrarContribuicaoPrevidenciaria(500);
    }

    @Test
	public void testeCalculaTotalImpostos() {
        assertEquals(987.88f, irpf.calculaTotalImpostos(), 0f);
	}
}
