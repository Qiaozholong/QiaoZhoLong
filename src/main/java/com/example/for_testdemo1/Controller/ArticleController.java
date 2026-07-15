package com.example.for_testdemo1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.ArticleCreateDto;
import com.example.for_testdemo1.Entity.ArticleEntity;
import com.example.for_testdemo1.Service.ArticleService;
import com.example.for_testdemo1.Vo.ArticleCreateVo;
import com.example.for_testdemo1.Vo.ArticleVersionVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //查询对应id编号文章
    @GetMapping("/getInfo/{id}")
    public Result<ArticleCreateVo> getArticleInfo(@PathVariable int id) {
        return articleService.getArticleInfoById(id);
    }
    //查询userid名下对应文章title
    @GetMapping("/show/{id}")
    public Result<List<ArticleVersionVo>> showArticle(@PathVariable int id) {
        return articleService.showArticle(id);
    }

    //创建文章
    @PostMapping("/createArticle")
    public Result<ArticleCreateVo> createArticle(@Valid @RequestBody ArticleCreateDto dto, HttpServletRequest requset) {
        int userId = (int)requset.getAttribute("userId");
        dto.setUserId(userId);
        return articleService.createArticle(dto,userId);
    }
    //删除文章
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteArticle(@PathVariable int id,HttpServletRequest requset) {
        int userId =(int)requset.getAttribute("userId");
        int userRole = (int)requset.getAttribute("role");
        return articleService.deleteArticle(id,userId,userRole);
    }
    //文章分页
    @GetMapping("/page/{current}")
    public Result<Page<ArticleVersionVo>>TurnPage(@PathVariable int current){
        return articleService.TurnPage(current);
    }


}
