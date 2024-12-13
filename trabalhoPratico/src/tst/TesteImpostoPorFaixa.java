package tst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteImpostoPorFaixa {

    IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
        
        // Cadastro dos rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 2259.49f);
    }

    @Test
	public void calculaTotalImpostos() {
        assertEquals(6810.41f, irpf.calcularBaseDeCalculo(), 0f);
	}
}
