package challenge.leandrini.domains.oportunidades.usecases.list;

import challenge.leandrini.domains.oportunidades.models.Opportunity;

import java.util.List;

public record ListOpportunitiesResult(
        List<Opportunity> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {}