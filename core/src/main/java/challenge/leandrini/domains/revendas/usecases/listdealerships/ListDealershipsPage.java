package challenge.leandrini.domains.revendas.usecases.listdealerships;

import challenge.leandrini.domains.revendas.models.Dealership;

import java.util.List;

public record ListDealershipsPage(
        List<Dealership> items,
        long totalElements
) {}
