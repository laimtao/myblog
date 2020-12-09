package com.lmt.service;

import com.lmt.domain.BlogAndTag;
import com.lmt.domain.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagService {

    Tag findTagByName(String name);

    List<Tag> getAllTags();

    Tag getTag(Long id);

    void addTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(Long id);

    String findTagByBlogId(Long id);

    List<Tag> getTagByString(String tagIds);

    List<Tag> getBlogTags();
}
