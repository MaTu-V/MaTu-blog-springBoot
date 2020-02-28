package com.mt.blog.controller;

import com.mt.blog.pojo.Comment;
import com.mt.blog.service.CommentService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private CommentService commentService;

    @Autowired
    BlogJSONResult result;

    @GetMapping("/selCommentReviewer")
    public BlogJSONResult selCommentReviewer(@RequestParam String articleId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize) {
        return result.ok(commentService.selCommentReviewer(articleId,page,pageSize)); // 获取数据
    }
    @GetMapping("/selWebSiteCommentReviewer")
    public BlogJSONResult selWebSiteCommentReviewer(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize) {
        return result.ok(commentService.selWebSiteCommentReviewer(page,pageSize)); // 获取数据
    }
    @PostMapping("/insComment")
    public BlogJSONResult insComment(@RequestBody Comment comment){
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<Boolean> serviceResult = commentService.insComment(comment,token);
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(),serviceResult.getMsg());
    }

}
