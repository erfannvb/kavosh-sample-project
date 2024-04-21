package nvb.dev.kavoshsampleproject.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseModel {

    private String title;
    private String detail;
    private List<String> details;
    private int status;
    private LocalDateTime timestamp;

}
