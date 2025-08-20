package challenge.leandrini.domains.revendas.usecases.listdealerships;

public record ListDealershipsQuery(
        int page,
        int size,
        String sortBy,
        String sortDir,
        String cnpj,
        String socialName,
        String q
) {}
