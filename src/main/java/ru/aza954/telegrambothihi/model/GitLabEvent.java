package ru.aza954.telegrambothihi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitLabEvent {
    @JsonProperty("object_kind")
    private String objectKind;

    @JsonProperty("object_attributes")
    private ObjectAttributes objectAttributes;

    @JsonProperty("project")
    private Project project;

    @Data
    public static class ObjectAttributes {
        private String state;
        private String action;  // Добавлено поле для типа действия
        @JsonProperty("target_branch")
        private String targetBranch;
    }

    @Data
    public static class Project {
        @JsonProperty("name")
        private String name;
    }
}
