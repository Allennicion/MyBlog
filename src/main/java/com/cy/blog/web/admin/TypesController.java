package com.cy.blog.web.admin;

import com.cy.blog.po.Type;
import com.cy.blog.service.TypesService;
import com.cy.blog.vo.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * User: 张文杰
 * Date: 2020/3/8
 * Time: 21:19
 * 新增类型控制层
 */
@Controller
@RequestMapping("/admin")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        Page<Type> page = typesService.listType(pageable);
        model.addAttribute("page", page);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    @GetMapping("/types/{id}/editTypes")
    public String editTypes(@PathVariable Long id, Model model) {
        model.addAttribute("type", typesService.getType(id));
        return "/admin/types-input";
    }

    @PostMapping("/inputTypes")
    @ResponseBody
    public AjaxJson inputTypes(@Valid Type type) {
        Type t = typesService.getTypeByName(type.getName());
        if (t != null) {
            return AjaxJson.fail("类型名称不能重复");
        }

        t = typesService.saveType(type);
        if (t == null) {
            return AjaxJson.fail("操作失败");
        } else {
            return AjaxJson.success("操作成功", "");
        }
    }

    @PostMapping("/inputTypes/{id}")
    @ResponseBody
    public AjaxJson inputTypes(@Valid Type type, @PathVariable Long id) {
        Type t = typesService.getTypeByName(type.getName());
        if (t != null) {
            return AjaxJson.fail("类型名称不能重复");
        }
        t = typesService.updateType(id, type);
        if (t == null) {
            return AjaxJson.fail("更新失败");
        } else {
            return AjaxJson.success("操作成功", "");
        }
    }

    @RequestMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typesService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
