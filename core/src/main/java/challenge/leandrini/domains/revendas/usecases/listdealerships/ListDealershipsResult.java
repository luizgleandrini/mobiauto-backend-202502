package challenge.leandrini.domains.revendas.usecases.listdealerships;

import challenge.leandrini.domains.revendas.models.Dealership;

import java.util.List;

public record ListDealershipsResult(
        List<Dealership> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {}