package com.cy.blog.web.admin;

import com.cy.blog.po.Blog;
import com.cy.blog.po.Tag;
import com.cy.blog.po.User;
import com.cy.blog.service.BlogService;
import com.cy.blog.service.TagService;
import com.cy.blog.service.TypesService;
import com.cy.blog.util.CommonUtils;
import com.cy.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * User: 张文杰
 * Date: 2020/3/8
 * Time: 19:45
 * Description: 博客跳转控制器
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypesService typesService;
    @Autowired
    private TagService tagService;


    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blogQuery, Model model) {
        model.addAttribute("types", typesService.listType());
        model.addAttribute("page", blogService.listBlog(pageable,blogQuery));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable,blogQuery));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typesService.listType());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typesService.listType());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs/post")
    public String post(Blog blog, HttpSession session, RedirectAttributes redirectAttributes) {
        blog.setUser((User) session.getAttribute("user"));
        if(blog.getType()!=null) {
            blog.setType(typesService.getType(blog.getType().getId()));
        }
        if(blog.getTagIds() != null) {
            String ids = checkTag(blog.getTagIds());
            blog.setTags(tagService.listTag(ids));
        }
        Blog b = null;
        if(blog.getId()!=null) {
            b = blogService.updateBlog(blog.getId(), blog);
        }else {
            b = blogService.saveBlog(blog);
        }
        if(b == null) {
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    private String checkTag(String tagIds) {
        StringBuilder sb = new StringBuilder();
        String[] tags = tagIds.split(",");
        String ids = "";
        for (String id : tags) {
            Tag t = new Tag();
            Boolean bool = CommonUtils.isNumeric(id);
            if(bool) {
                Tag tag = tagService.getTag(Long.parseLong(id));
                if(tag == null) {
                    t.setName(id);
                    t = tagService.saveAndFlush(t);
                    ids = t.getId()+"";
                }else {
                    ids = id;
                }
            }else {
                t.setName(id);
                t = tagService.saveAndFlush(t);
                ids = t.getId()+"";
            }
            sb.append(ids+",");
        }
        return sb.toString().substring(0,sb.toString().length() -1);
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
