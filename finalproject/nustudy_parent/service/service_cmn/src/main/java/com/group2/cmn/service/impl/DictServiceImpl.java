package com.group2.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.group2.cmn.listener.DictListener;
import com.group2.cmn.mapper.DictMapper;
import com.group2.cmn.service.DictService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.cmn.Dict;
import com.group2.nustudy.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //lookup child data list based on id
    @Override
    //@Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChlidData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }


    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        // 0>0    1>0
        return count > 0;
    }

    // export the dictData
    @Override
    public void exportDict(HttpServletResponse response) {
        try {
            // Downloading set
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = "dict";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // lookup db
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictEeVoList = new ArrayList<>();
            for (Dict dict : dictList) {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictEeVo);
                dictEeVoList.add(dictEeVo);
            }
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictEeVoList);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void importDict(MultipartFile file) {
        DictListener dictListener = new DictListener(baseMapper);
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, dictListener).sheet().doRead();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public String getDictName(String dictCode, String value) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        System.out.println("use wrapper: " + value + ", " + dictCode);
        if (StringUtils.isEmpty(dictCode)) {
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            System.out.println("find dict: " + dict);
            return dict.getName();
        } else {
            wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code", dictCode);
            Dict codeDict = baseMapper.selectOne(wrapper);
            Long id = codeDict.getId();
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", id)
                    .eq("value", value));
            return finalDict.getName();
        }
    }
}
