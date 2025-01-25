package app;



public class IRPF {

    public static final boolean TRIBUTAVEL = true;
    public static final boolean NAOTRIBUTAVEL = false;
	
	// Rendimentos
	private Rendimentos rendimentos;
	private Deducoes deducoes;
	private Dependentes dependentes;

	public IRPF() {
        this.rendimentos = new Rendimentos();
        this.deducoes = new Deducoes();
        this.dependentes = new Dependentes();
	}
    public IRPF(Rendimentos rendimentos, Deducoes deducoes, Dependentes dependentes) {
		this.rendimentos = rendimentos;
		this.dependentes = dependentes;
		this.deducoes = deducoes;
    }

	public void cadastrarDependente(String nome, String parentesco) {
		// adicionar dependente 
		String[] temp = new String[nomesDependentes.length + 1];
		for (int i=0; i<nomesDependentes.length; i++) {
			temp[i] = nomesDependentes[i];
		}
		temp[nomesDependentes.length] = nome;
		nomesDependentes = temp;
		
		String[] temp2 = new String[parentescosDependentes.length + 1];
		for (int i=0; i<parentescosDependentes.length; i++) {
			temp2[i] = parentescosDependentes[i];
		}
		temp2[parentescosDependentes.length] = parentesco;
		parentescosDependentes = temp2;
		
		numDependentes++;
	}
	/**
	 * Método que retorna o numero de dependentes do contribuinte
	 * @return numero de dependentes
	 */
	public int getNumDependentes() {
		return numDependentes;
	}
	
	/**
	 * Return o valor do total de deduções para o contribuinte
	 * @return valor total de deducoes
	 */
	public float getDeducao() {
		float total = 0; 
		for (String d: nomesDependentes) {
			total += 189.59f;
		}
		total += totalContribuicaoPrevidenciaria;
		
		return total;
	}

	/**
	 * Cadastra um valor de contribuição previdenciária oficial
	 * @param contribuicao valor da contribuição previdenciária oficial
	 */
	public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
		numContribuicaoPrevidenciaria++;
		totalContribuicaoPrevidenciaria += contribuicao;
	}

	/**
	 * Retorna o numero total de contribuições realizadas como contribuicao 
	 * previdenciaria oficial
	 * @return numero de contribuições realizadas
	 */
	public int getNumContribuicoesPrevidenciarias() {
		return numContribuicaoPrevidenciaria;
	}

	/**
	 * Retorna o valor total de contribuições oficiais realizadas
	 * @return valor total de contribuições oficiais
	 */
	public float getTotalContribuicoesPrevidenciarias() {
		return totalContribuicaoPrevidenciaria;
	}

	/**
	 * Realiza busca do dependente no cadastro do contribuinte
	 * @param nome nome do dependente que está sendo pesquisado
	 * @return nome do dependente ou null, caso nao conste na lista de dependentes
	 */
	public String getDependente(String nome) {
		for (String d : nomesDependentes) {
			if (d.contains(nome))
				return d;
		}
		return null;
	}

	/**
	 * Método que retorna o grau de parentesco para um dado dependente, caso ele
	 * conste na lista de dependentes
	 * @param dependente nome do dependente
	 * @return grau de parentesco, nulo caso nao exista o dependente
	 */
	public String getParentesco(String dependente) {
		for (int i = 0; i<nomesDependentes.length; i++) {
			if (nomesDependentes[i].equalsIgnoreCase(dependente))
				return parentescosDependentes[i];
		}
		return null;
	}

	/**
	 * Realiza o cadastro de uma pensao alimenticia para um dos dependentes do 
	 * contribuinte, caso ele seja um filho ou alimentando. 
	 * @param dependente nome do dependente 
	 * @param valor valor da pensao alimenticia
	 */
	public void cadastrarPensaoAlimenticia(String dependente, float valor) {
		String parentesco = getParentesco(dependente); 
		if (parentesco.toLowerCase().contains("filh") || 
			parentesco.toLowerCase().contains("alimentand")) {
			totalPensaoAlimenticia += valor;
		}
	}

	/**
	 * Retorna o valor total pago em pensões alimentícias pelo contribuinte.
	 * @return valor total de pensoes alimenticias
	 */
	public float getTotalPensaoAlimenticia() {
		return totalPensaoAlimenticia;
	}

	
	/**
	 * Metodo para cadastrar deduções integrais para o contribuinte. Para cada
	 * dedução é informado seu nome e valor. 
	 * @param nome nome da deducao 
	 * @param valorDeducao valor da deducao
	 */
	public void cadastrarDeducaoIntegral(String nome, float valorDeducao) {
		String temp[] = new String[nomesDeducoes.length + 1];
		for (int i=0; i<nomesDeducoes.length; i++) {
			temp[i] = nomesDeducoes[i]; 
		}
		temp[nomesDeducoes.length] = nome;
		nomesDeducoes = temp;
		
		float temp2[] = new float[valoresDeducoes.length + 1];
		for (int i=0; i<valoresDeducoes.length; i++) {
			temp2[i] = valoresDeducoes[i]; 
		}
		temp2[valoresDeducoes.length] = valorDeducao;
		valoresDeducoes = temp2;
	}

	
	/**
	 * Método para pesquisar uma deducao pelo seu nome. 
	 * @param substring do nome da deducao a ser pesquisada
	 * @return nome da deducao, ou null caso na esteja cadastrada
	 */
	public String getOutrasDeducoes(String nome) {
		for (String d : nomesDeducoes) {
			if (d.toLowerCase().contains(nome.toLowerCase()))
				return d;
		}
		return null;
	}

	/**
	 * Obtem o valor da deducao à partir de seu nome 
	 * @param nome nome da deducao para a qual se busca seu valor
	 * @return valor da deducao
	 */
	public float getDeducao(String nome) {
		for (int i=0; i<nomesDeducoes.length; i++) {
			if (nomesDeducoes[i].toLowerCase().contains(nome.toLowerCase()))
				return valoresDeducoes[i];
		}
		return 0;
	}

	/**
	 * Obtem o valor total de todas as deduções que nao sao do tipo
	 * contribuicoes previdenciarias ou por dependentes
	 * @return valor total das outras deducoes
	 */
	public float getTotalOutrasDeducoes() {
		float soma = 0;
		for (float f : valoresDeducoes) {
			soma += f;
		}
		return soma;
	}


	// Rendimentos
    public void criarRendimento(String nome, boolean tributavel, float valor) {
        rendimentos.criarRendimento(nome, tributavel, valor);
    }
    public int getNumRendimentos() {
        return rendimentos.getNumRendimentos();
    }
    public float getTotalRendimentos() {
        return rendimentos.getTotalRendimentos();
    }
    public float getTotalRendimentosTributaveis() {
        return rendimentos.getTotalRendimentosTributaveis();
    }



	/** 
	 * Calcula a base de cálculo do imposto, que é o valor total dos rendimentos 
	 * tributáveis menos as deduções permitidas. 
	 * @return base de cálculo do imposto 
	 */ 
	public float calcularBaseDeCalculo() { 
	    // Calcular o total de rendimentos tributáveis
	    float totalDeRendimentos = rendimentos.getTotalRendimentosTributaveis(); 
	    
	    // Calcular as deduções totais (dependentes, contribuições previdenciárias, etc.)
	    float totalDeDeducoes = getDeducao() + getTotalOutrasDeducoes(); 
	    
	    // Pensão alimentícia deve ser considerada como uma dedução
	    float totalPensaoAlimenticia = getTotalPensaoAlimenticia();
	    
	    // Subtrair as deduções e a pensão alimentícia da base de cálculo
	    float baseCalculo = totalDeRendimentos - totalDeDeducoes - totalPensaoAlimenticia;
	    
	    // Se a base de cálculo for negativa, ela deve ser ajustada para zero
	    return Math.max(baseCalculo, 0.0f); // Evita que a base de cálculo fique negativa
	}

	// Pega a porcentagem da faixa
	public float getPorcentagemFaixa(){
        FaixaAliquota faixa = new FaixaAliquota();
        return faixa.getPorcentagemFaixa(calcularBaseDeCalculo());
	}


	// Calcula Total Impostos
	public float calculaTotalImpostos() {
        CalculoImposto calculo = new CalculoImposto(calcularBaseDeCalculo());
        return calculo.calcularImpostos();
    }

	/**
	 * Aliquota efetiva do imposto pago 
	 * @param impostoDevido
	 * @return aliquotaEfetiva
	 */
	public float aliquotaEfetiva() {
		// Calcular o imposto devido
		float impostoDevido = calculaTotalImpostos();
	
		// Obter o total de rendimentos tributáveis
		float totalRendimentosTributaveis = rendimentos.getTotalRendimentosTributaveis();
	
		// Evitar divisão por zero
		if (totalRendimentosTributaveis == 0) {
			return 0.0f;
		}
	
		// Calcular a alíquota efetiva
		return (impostoDevido / totalRendimentosTributaveis) * 100;
	}

}

// Extração da função calcularImpostos() da classe IRPF para virar um objeto-método 
class CalculoImposto {
    private final float baseDeCalculo;

    public CalculoImposto(float baseDeCalculo) {
        this.baseDeCalculo = baseDeCalculo;
    }

    public float calcularImpostos() {
        float[] faixas = {2259.20f, 2826.65f, 3751.05f, 4664.68f};
        float[] aliquotas = {0.0f, 0.075f, 0.15f, 0.225f, 0.275f};
        float imposto = 0.0f;
        float aux;

        if (baseDeCalculo <= faixas[0]) {
            return 0.0f;
        }

        if (baseDeCalculo > faixas[0] && baseDeCalculo <= faixas[1]) {
            aux = baseDeCalculo - faixas[0];
            return aux * aliquotas[1];
        }

        imposto += 42.59f;

        if (baseDeCalculo > faixas[1] && baseDeCalculo <= faixas[2]) {
            aux = baseDeCalculo - faixas[1];
            return imposto + (aux * aliquotas[2]);
        }

        imposto += 138.66f;

        if (baseDeCalculo > faixas[2] && baseDeCalculo <= faixas[3]) {
            aux = baseDeCalculo - faixas[2];
            return imposto + (aux * aliquotas[3]);
        }

        imposto += 205.57f;

        aux = baseDeCalculo - faixas[3];
        return imposto + (aux * aliquotas[4]);
    }
}

// Classe Faixa Aliquota
class FaixaAliquota {
    public float getPorcentagemFaixa(float salario) {
        if (salario <= 2259.20f) {
            return 0.0f;
        } else if (salario <= 2826.65f) {
            return 0.075f;
        } else if (salario <= 3751.05f) {
            return 0.15f;
        } else if (salario <= 4664.68f) {
            return 0.225f;
        } else {
            return 0.275f;
        }
    }
}


// Classe para gerenciar rendimentos
class Rendimentos {

    private String[] nomeRendimento;
    private boolean[] rendimentoTributavel;
    private float[] valorRendimento;
    private int numRendimentos;
    private float totalRendimentos;

    public Rendimentos() {
        this.nomeRendimento = new String[0];
        this.rendimentoTributavel = new boolean[0];
        this.valorRendimento = new float[0];
        this.numRendimentos = 0;
        this.totalRendimentos = 0;
    }

    public void criarRendimento(String nome, boolean tributavel, float valor) {
        // Adicionar o nome do novo rendimento
        String[] temp = new String[nomeRendimento.length + 1];
        for (int i = 0; i < nomeRendimento.length; i++) {
            temp[i] = nomeRendimento[i];
        }
        temp[nomeRendimento.length] = nome;
        nomeRendimento = temp;

        // Adicionar tributável ou não no vetor
        boolean[] temp2 = new boolean[rendimentoTributavel.length + 1];
        for (int i = 0; i < rendimentoTributavel.length; i++) {
            temp2[i] = rendimentoTributavel[i];
        }
        temp2[rendimentoTributavel.length] = tributavel;
        rendimentoTributavel = temp2;

        // Adicionar valor rendimento ao vetor
        float[] temp3 = new float[valorRendimento.length + 1];
        for (int i = 0; i < valorRendimento.length; i++) {
            temp3[i] = valorRendimento[i];
        }
        temp3[valorRendimento.length] = valor;
        valorRendimento = temp3;

        this.numRendimentos += 1;
        this.totalRendimentos += valor;
    }

    public int getNumRendimentos() {
        return numRendimentos;
    }

    public float getTotalRendimentos() {
        return totalRendimentos;
    }

    public float getTotalRendimentosTributaveis() {
        float totalRendimentosTributaveis = 0;
        for (int i = 0; i < rendimentoTributavel.length; i++) {
            if (rendimentoTributavel[i]) {
                totalRendimentosTributaveis += valorRendimento[i];
            }
        }
        return totalRendimentosTributaveis;
    }
}

class Dependentes {
	private String[] nomesDependentes;
	private String[] parentescosDependentes;
	private int numDependentes;
	private float totalPensaoAlimenticia;

}

class Deducoes {
	private int numContribuicaoPrevidenciaria;
	private float totalContribuicaoPrevidenciaria;
	private String[] nomesDeducoes;
	private float[] valoresDeducoes;
}