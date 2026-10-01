package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ReservaCarritoRequest(@NotEmpty @Size(max = 100) @Valid
    List<ReservaItemRequest> items) {
}
