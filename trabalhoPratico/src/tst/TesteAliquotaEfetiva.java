package tst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteAliquotaEfetiva {
    IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
    }

	
	@Test
	public void testCalcularAliquotaEfetivaComValoresMedios() {
		irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000);
		irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, 2000);
		irpf.cadastrarDeducaoIntegral("Contribuição previdenciária", 1000);
		irpf.cadastrarContribuicaoPrevidenciaria(500);
		irpf.cadastrarDependente("Filho", "filho");
		irpf.cadastrarPensaoAlimenticia("Filho", 1500);
	
		assertEquals(9.76f, irpf.aliquotaEfetiva(), 0.01f);
	}
	
	
    @Test
    public void testCalcularAliquotaEfetivaSemRendimentos() {
        assertEquals(0f, irpf.aliquotaEfetiva(), 0.01f);
    }

    @Test
    public void testCalcularAliquotaEfetivaComApenasRendimentoIsento() {
		irpf.criarRendimento("Rendimento nulo", IRPF.TRIBUTAVEL, 0);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, 3000);
        assertEquals(0f, irpf.aliquotaEfetiva(), 0.0f);
    }

    @Test
    public void testCalcularAliquotaEfetivaComDeducoesAltas() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 5000);
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária", 3000);
        irpf.cadastrarContribuicaoPrevidenciaria(2000);
        assertEquals(0f, irpf.aliquotaEfetiva(), 0f);
    }

    @Test
    public void testCalcularAliquotaEfetivaComAltosRendimentos() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 50000);
        irpf.criarRendimento("Investimentos", IRPF.TRIBUTAVEL, 20000);
        irpf.cadastrarDeducaoIntegral("Contribuição previdenciária", 10000);
        irpf.cadastrarContribuicaoPrevidenciaria(5000);
        irpf.cadastrarDependente("Filho", "filho");
        irpf.cadastrarDependente("Cônjuge", "esposa");
        assertEquals(20.17f, irpf.aliquotaEfetiva(), 0.01f);
    }

    @Test
    public void testCalcularAliquotaEfetivaComMultiplosDependentes() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 15000);
        irpf.cadastrarDependente("Filho", "filho");
        irpf.cadastrarDependente("Filha", "filha");
        irpf.cadastrarDependente("Esposa", "esposa");
        irpf.cadastrarContribuicaoPrevidenciaria(2000);
        assertEquals(16.81f, irpf.aliquotaEfetiva(), 0.01f);
    }
}
