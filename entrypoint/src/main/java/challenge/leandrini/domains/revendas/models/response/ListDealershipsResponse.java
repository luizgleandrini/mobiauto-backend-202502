package challenge.leandrini.domains.revendas.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListDealershipsResponse {

    private List<DealershipItem> items;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean hasNext;

    private boolean hasPrevious;

}
