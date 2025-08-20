package challenge.leandrini.domains.oportunidades.usecases.list;

public record ListOpportunitiesQuery(
        int page,
        int size,
        String sortBy,
        String sortDir,
        String dealershipId,
        String status,
        String clientName,
        String clientEmail,
        String vehicleBrand,
        String q
) {}