// Refatoração da classe IRPF e alterações solicitadas

package app;

import java.util.ArrayList;
import java.util.List;

public class IRPF {

    public static final boolean TRIBUTAVEL = true;
    public static final boolean NAOTRIBUTAVEL = false;

    private List<Rendimento> rendimentos;
    private List<Dependente> dependentes;
    private List<Deducao> outrasDeducoes;
    private float totalPensaoAlimenticia;

    public IRPF() {
        this.rendimentos = new ArrayList<>();
        this.dependentes = new ArrayList<>();
        this.outrasDeducoes = new ArrayList<>();
        this.totalPensaoAlimenticia = 0f;
    }

    public void criarRendimento(String nome, boolean tributavel, float valor) {
        rendimentos.add(new Rendimento(nome, tributavel, valor));
    }

    public float getTotalRendimentos() {
        return rendimentos.stream().mapToFloat(Rendimento::getValor).sum();
    }

    public float getTotalRendimentosTributaveis() {
        return rendimentos.stream().filter(Rendimento::isTributavel).mapToFloat(Rendimento::getValor).sum();
    }

    public void cadastrarDependente(String nome, String parentesco) {
        dependentes.add(new Dependente(nome, parentesco));
    }

    public float getDeducaoPorDependentes() {
        return dependentes.size() * 189.59f;
    }

    public void cadastrarDeducaoIntegral(String nome, float valor) {
        outrasDeducoes.add(new Deducao(nome, valor));
    }

    public float getTotalOutrasDeducoes() {
        return outrasDeducoes.stream().mapToFloat(Deducao::getValor).sum();
    }

    public void cadastrarPensaoAlimenticia(String dependente, float valor) {
        if (dependentes.stream().anyMatch(d -> d.getNome().equalsIgnoreCase(dependente))) {
            totalPensaoAlimenticia += valor;
        }
    }

    public float getTotalPensaoAlimenticia() {
        return totalPensaoAlimenticia;
    }

    public float calcularBaseDeCalculo() {
        float rendimentosTributaveis = getTotalRendimentosTributaveis();
        float deducoes = getDeducaoPorDependentes() + getTotalOutrasDeducoes() + totalPensaoAlimenticia;
        return Math.max(rendimentosTributaveis - deducoes, 0);
    }

    public float calcularAliquota(float baseDeCalculo) {
        if (baseDeCalculo <= 2259.20f) return 0.0f;
        if (baseDeCalculo <= 2826.65f) return 0.075f;
        if (baseDeCalculo <= 3751.05f) return 0.15f;
        if (baseDeCalculo <= 4664.68f) return 0.225f;
        return 0.275f;
    }

    public float getPorcentagemFaixa() {
        return calcularAliquota(calcularBaseDeCalculo());
    }

    public TotalImpostos calculaTotalImpostos() {
        return new TotalImpostos(calcularBaseDeCalculo());
    }
}

class Rendimento {
    private String nome;
    private boolean tributavel;
    private float valor;

    public Rendimento(String nome, boolean tributavel, float valor) {
        this.nome = nome;
        this.tributavel = tributavel;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public boolean isTributavel() {
        return tributavel;
    }

    public float getValor() {
        return valor;
    }
}

class Dependente {
    private String nome;
    private String parentesco;

    public Dependente(String nome, String parentesco) {
        this.nome = nome;
        this.parentesco = parentesco;
    }

    public String getNome() {
        return nome;
    }
}

class Deducao {
    private String nome;
    private float valor;

    public Deducao(String nome, float valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }
}

class TotalImpostos {
    private float baseDeCalculo;
    private float totalImpostos;

    public TotalImpostos(float baseDeCalculo) {
        this.baseDeCalculo = baseDeCalculo;
        calcularImpostos();
    }

    private void calcularImpostos() {
        float[] limites = {2259.20f, 2826.65f, 3751.05f, 4664.68f};
        float[] aliquotas = {0.075f, 0.15f, 0.225f, 0.275f};
        float imposto = 0;

        for (int i = limites.length - 1; i >= 0; i--) {
            if (baseDeCalculo > limites[i]) {
                imposto += (baseDeCalculo - limites[i]) * aliquotas[i];
                baseDeCalculo = limites[i];
            }
        }
        this.totalImpostos = imposto;
    }

    public float getTotalImpostos() {
        return totalImpostos;
    }
}