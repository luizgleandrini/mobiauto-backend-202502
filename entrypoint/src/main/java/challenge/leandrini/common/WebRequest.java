package challenge.leandrini.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebRequest implements Serializable {
    protected String path;
}
