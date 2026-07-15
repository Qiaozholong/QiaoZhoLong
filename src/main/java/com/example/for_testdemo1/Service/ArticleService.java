package com.example.for_testdemo1.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.ArticleCreateDto;
import com.example.for_testdemo1.Dto.ResetArticleDto;
import com.example.for_testdemo1.Entity.ArticleEntity;
import com.example.for_testdemo1.Vo.ArticleCreateVo;
import com.example.for_testdemo1.Vo.ArticleVersionVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ArticleService extends IService<ArticleEntity> {
    //article创建入口
    Result<ArticleCreateVo> createArticle(ArticleCreateDto dto,int userId);
    //article对应用户id查询名下文章入口(目前只支持查id，推测未来方向做到用id，name模糊查询？)
    Result<List<ArticleVersionVo>> showArticle(int userId);
    //article信息查询入口
    Result<ArticleCreateVo> getArticleInfoById(int id);
    //article删除文章入口
    Result<Void> deleteArticle(int id,int userId,int userRole);
    //article分页添加入口
    Result<Page<ArticleVersionVo>> TurnPage(int current);
    //article文章编辑入口
    Result<ArticleCreateVo> ResetArticle(ResetArticleDto dto, int articleId,int userId,int userRole);
}
