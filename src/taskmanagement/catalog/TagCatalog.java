package taskmanagement.catalog;

import taskmanagement.domain.Tag;

import java.util.List;
import java.util.ArrayList;

public class TagCatalog {
    private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag) {
        // Check if tag already exists
        if (!tagExists(tag.getName())) {
            tags.add(tag);
        }
    }

    public Tag findOrCreateTag(String tagName) {
        for (Tag t : tags) {
            if (t.getName().equals(tagName)) {
                return t;
            }
        }
        // Create and add new tag if it doesn't exist
        Tag newTag = new Tag(tagName);
        tags.add(newTag);
        return newTag;
    }

    public boolean tagExists(String tagName) {
        for (Tag t : tags) {
            if (t.getName().equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    public Tag findTag(String tagName) {
        for (Tag t : tags) {
            if (t.getName().equals(tagName)) {
                return t;
            }
        }
        return null;
    }

    public List<Tag> getAllTags() {
        return new ArrayList<>(tags);
    }

    public void deleteTag(String tagName) {
        tags.removeIf(t -> t.getName().equals(tagName));
    }
}
