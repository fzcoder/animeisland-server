package com.animeisland.controller;

import com.animeisland.entity.Category;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.animeisland.bean.BaseResponse;
import com.animeisland.bean.PageQuery;
import com.animeisland.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/category")
    public Object saveCategory(@RequestBody Category category) {
        if (categoryService.save(category)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "添加成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "添加失败！");
        }
    }

    @GetMapping("/admin/category/{id}")
    public Object getCategory(@PathVariable("id") Serializable id) {
        return new BaseResponse(categoryService.getById(id));
    }

    @GetMapping("/category")
    public Object getList(@RequestParam Map<String, Object> params) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        String[] sqlSelect = {"name", "value"};
        queryWrapper.select(sqlSelect);
        return new BaseResponse(categoryService.listMaps(queryWrapper));
    }

    @PostMapping("/category")
    public Object getPage(@RequestParam Map<String, Object> params, @RequestBody PageQuery pageQuery) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        queryWrapper.like(true, "name", pageQuery.getKey());
        queryWrapper.orderBy(true, !pageQuery.isReverse(), pageQuery.getOrderBy());
        IPage<Map<String, Object>> page = categoryService.pageMaps(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        return new BaseResponse(page);
    }

    @PutMapping("/admin/category")
    public Object updateCategory(@RequestBody Category category) {
        if (categoryService.updateById(category)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "修改成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败！");
        }
    }

    @DeleteMapping("/admin/category/{id}")
    public Object removeCategory(@PathVariable("id") Serializable id) {
        if (categoryService.removeById(id)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "删除失败！");
        }
    }

    @DeleteMapping("/admin/category")
    public Object removeBatch(@RequestParam Map<Integer, Serializable> params) {
        if (categoryService.removeByIds(new ArrayList<>(params.values()))) {
            return new BaseResponse(HttpServletResponse.SC_OK, "批量删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量删除失败！");
        }
    }
}
