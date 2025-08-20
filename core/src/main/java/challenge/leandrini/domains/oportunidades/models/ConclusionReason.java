package challenge.leandrini.domains.oportunidades.models;

public enum ConclusionReason {
    VENDA_REALIZADA("venda_realizada"),
    SEM_INTERESSE("sem_interesse"),
    PRECO_INADEQUADO("preco_inadequado"),
    VEICULO_INDISPONIVEL("veiculo_indisponivel"),
    CLIENTE_NAO_RETORNOU("cliente_nao_retornou"),
    OUTROS("outros");

    private final String value;

    ConclusionReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}