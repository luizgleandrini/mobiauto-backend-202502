package challenge.leandrini.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseStatus {

    private LocalDateTime timestamp;

    private long status;

    private String error;

    private String message;

    private String path;
}
