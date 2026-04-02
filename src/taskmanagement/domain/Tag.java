package taskmanagement.domain;

public class Tag {
    private Long tagId;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
