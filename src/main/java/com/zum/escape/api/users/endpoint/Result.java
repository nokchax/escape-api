package com.zum.escape.api.users.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty("file_id")
    private String fileId;
    @JsonProperty("file_size")
    private Integer fileSize;
    @JsonProperty("file_path")
    private String filePath;
}
