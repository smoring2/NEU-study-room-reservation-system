package com.group2.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
public interface DictService extends IService<Dict> {

    //Query sub-data list based on data id
    List<Dict> findChildData(Long id);

    void exportDict(HttpServletResponse response);
    void importDict(MultipartFile file) throws IOException;
    String getDictName(String dictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
