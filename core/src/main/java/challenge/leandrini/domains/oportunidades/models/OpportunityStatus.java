package challenge.leandrini.domains.oportunidades.models;

public enum OpportunityStatus {
    NOVO("novo"),
    EM_ATENDIMENTO("em_atendimento"),
    CONCLUIDO("concluido");

    private final String value;

    OpportunityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}