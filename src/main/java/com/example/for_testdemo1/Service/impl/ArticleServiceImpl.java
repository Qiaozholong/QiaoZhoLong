package com.example.for_testdemo1.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.for_testdemo1.Entity.ArticleEntity;
import com.example.for_testdemo1.Mapper.ArticleMapper;
import com.example.for_testdemo1.Service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

}
