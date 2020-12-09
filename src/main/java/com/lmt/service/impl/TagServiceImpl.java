package com.lmt.service.impl;

import com.lmt.dao.TagDao;
import com.lmt.domain.Tag;
import com.lmt.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Override
    public Tag findTagByName(String name) {
        return tagDao.findTagByName(name);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTagById(id);
    }

    @Override
    public void addTag(Tag tag) {
        tagDao.addTag(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
    }

    @Override//获取一个博客的标签并将标签转换为字符串
    public String findTagByBlogId(Long id) {
        List<Integer> selectTags=tagDao.findTagByBlogId(id);
        if(!selectTags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag=false;
            for (Integer tag : selectTags) {
                if (flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                String tag1=String.valueOf(tag);
                ids.append(tag1);
            }
            return ids.toString();
        }
        return null;
    }

    @Override//通过拼接好的标签字符串获取List<Tag>，以便存入关联表
    public List<Tag> getTagByString(String tagIds) {
        List<Tag> tagList=new ArrayList<>();
        List<Long> longs=new ArrayList<>();
        if (!"".equals(tagIds)&&tagIds!=null){
            String[] array=tagIds.split(",");
            for (int i=0;i<array.length;i++){
                longs.add(Long.valueOf(array[i]));
            }
        }
        for(Long lg:longs){
            tagList.add(tagDao.getTagById(lg));
        }
        return tagList;
    }

    @Override
    public List<Tag> getBlogTags() {
        return tagDao.getBlogTags();
    }
}
