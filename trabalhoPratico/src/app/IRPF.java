package app;

public class IRPF {

	public static final boolean TRIBUTAVEL = true;
	public static final boolean NAOTRIBUTAVEL = false;
	private String[] nomeRendimento;
	private boolean[] rendimentoTributavel;
	private float[] valorRendimento;
	private int numRendimentos;
	private float totalRendimentos;
	
	private String[] nomesDependentes;
	private String[] parentescosDependentes;
	private int numDependentes;
	
	private int numContribuicaoPrevidenciaria;
	private float totalContribuicaoPrevidenciaria;
	
	private float totalPensaoAlimenticia;
	
	private String[] nomesDeducoes;
	private float[] valoresDeducoes;

	public IRPF() {
		nomeRendimento = new String[0];
		rendimentoTributavel = new boolean[0];
		valorRendimento = new float[0];
		
		nomesDependentes = new String[0];
		parentescosDependentes = new String[0];
		numDependentes = 0;
		
		numContribuicaoPrevidenciaria = 0; 
		totalContribuicaoPrevidenciaria = 0f;
		
		totalPensaoAlimenticia = 0f;
		
		nomesDeducoes = new String[0];
		valoresDeducoes = new float[0];
	}
	
	/**
	 * Cadastra um rendimento na base do contribuinte, informando o nome do 
	 * rendimento, seu valor e se ele é tributável ou não. 
	 * @param nome nome do rendimento a ser cadastrado
	 * @param tributavel true caso seja tributável, false caso contrário
	 * @param valor valor do rendimento a ser cadastrado
	 */
	public void criarRendimento(String nome, boolean tributavel, float valor) {
		//Adicionar o nome do novo rendimento
		String[] temp = new String[nomeRendimento.length + 1];
		for (int i=0; i<nomeRendimento.length; i++)
			temp[i] = nomeRendimento[i];
		temp[nomeRendimento.length] = nome;
		nomeRendimento = temp;

		//adicionar tributavel ou nao no vetor 
		boolean[] temp2 = new boolean[rendimentoTributavel.length + 1];
		for (int i=0; i<rendimentoTributavel.length; i++) 
			temp2[i] = rendimentoTributavel[i];
		temp2[rendimentoTributavel.length] = tributavel;
		rendimentoTributavel = temp2;
		
		//adicionar valor rendimento ao vetor
		float[] temp3 = new float[valorRendimento.length + 1];
		for (int i=0; i<valorRendimento.length; i++) {
			temp3[i] = valorRendimento[i];
		}
		temp3[valorRendimento.length] = valor; 
		valorRendimento = temp3;
		
		this.numRendimentos += 1;
		this.totalRendimentos += valor;
		
	}

	/**
	 * Retorna o número de rendimentos já cadastrados para o contribuinte
	 * @return numero de rendimentos
	 */
	public int getNumRendimentos() {
		return numRendimentos;
	}

	/**
	 * Retorna o valor total de rendimentos cadastrados para o contribuinte
	 * @return valor total dos rendimentos
	 */
	public float getTotalRendimentos() {
		return totalRendimentos;
	}

	/**
	 * Retorna o valor total de rendimentos tributáveis do contribuinte
	 * @return valor total dos rendimentos tributáveis
	 */
	public float getTotalRendimentosTributaveis() {
		float totalRendimentosTributaveis = 0;
		for (int i=0; i<rendimentoTributavel.length; i++) {
			if (rendimentoTributavel[i]) {
				totalRendimentosTributaveis += valorRendimento[i];
			}
		}
		return totalRendimentosTributaveis;
	}

	/**
	 * Método para realizar o cadastro de um dependente, informando seu grau 
	 * de parentesco
	 * @param nome Nome do dependente
	 * @param parentesco Grau de parentesco
	 */
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

	// OBS
	
	/*
	 * - a base de cálculo do imposto, 
	 * - os impostos por faixas e o total do imposto, 
	 * - e a alíquota efetiva do imposto pago. 
	*/
	
	/* func LUCAS - a base de cálculo do imposto, */ 
	/*
	 * Aliquota efetiva do imposto pago 
	 * @param 
	 * @return
	 */

	/** 
	 * Calcula a base de cálculo do imposto, que é o valor total dos rendimentos 
	 * tributáveis menos as deduções permitidas. 
	 * @return base de cálculo do imposto 
	 */ 
	public float calcularBaseDeCalculo() { 
	    // Calcular o total de rendimentos tributáveis
	    float totalDeRendimentos = getTotalRendimentosTributaveis(); 
	    
	    // Calcular as deduções totais (dependentes, contribuições previdenciárias, etc.)
	    float totalDeDeducoes = getDeducao() + getTotalOutrasDeducoes(); 
	    
	    // Pensão alimentícia deve ser considerada como uma dedução
	    float totalPensaoAlimenticia = getTotalPensaoAlimenticia();
	    
	    // Subtrair as deduções e a pensão alimentícia da base de cálculo
	    float baseCalculo = totalDeRendimentos - totalDeDeducoes - totalPensaoAlimenticia;
	    
	    // Se a base de cálculo for negativa, ela deve ser ajustada para zero
	    return Math.max(baseCalculo, 0.0f); // Evita que a base de cálculo fique negativa
	}

	
	public float getPorcentagemFaixa(){
		float salario = calcularBaseDeCalculo();
		float aliquota = 0.0f;
		
		if (salario <= 2259.20f){
			aliquota = 0.0f;
		}
		
		else if (salario > 2259.20f && salario <= 2826.65f){
			aliquota = 0.075f;
		}
		
		else if (salario > 2826.65f && salario <= 3751.05f){
			aliquota = 0.15f;
		}
		
		else if (salario > 3751.05f && salario <= 4664.68f){
			aliquota = 0.225f;
		}

		else{
			aliquota = 0.275f;
		}

		return aliquota;
	}


	/*func LIMIRIO - os impostos por faixas e o total do imposto,*/
	public float calculaTotalImpostos() {
		float salario = calcularBaseDeCalculo();

		float faixas[] = {
			2259.20f, 
			2826.65f, 
			3751.05f,
			4664.68f
		};

		float aliquota[] = {
			0.0f,
			0.075f,
			0.15f,
			0.225f,
			0.275f
		};

		float aux = 0.0f;
		float imposto = 0.0f;
		
		if (salario <= faixas[0]){
			return 0.0f;
		}
		
		if (salario > faixas[0] && salario >= faixas[1]){
			imposto += 42.59f;
		} else {
			aux = salario - faixas[0];
			aux *= aliquota[1];
			return imposto + aux;
		}
		
		if (salario > faixas[1] && salario >= faixas[2]){
			imposto += 138.66f;
		} else {
			aux = salario - faixas[1];
			aux *= aliquota[2];
			return imposto + aux;
		}
		
		if (salario > faixas[2] && salario >= faixas[3]){
			imposto += 205.57;
		}else{
			aux = salario - faixas[2];
			aux *= aliquota[3];
			return imposto + aux;
		}

		aux = salario - faixas[3];
		aux *= aliquota[4];
		return imposto + aux;
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
		float totalRendimentosTributaveis = getTotalRendimentosTributaveis();
	
		// Evitar divisão por zero
		if (totalRendimentosTributaveis == 0) {
			return 0.0f;
		}
	
		// Calcular a alíquota efetiva
		return (impostoDevido / totalRendimentosTributaveis) * 100;
	}

}
// ( ͡° ͜ʖ ͡°)