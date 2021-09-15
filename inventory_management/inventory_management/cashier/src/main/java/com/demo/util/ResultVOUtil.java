package com.demo.util;

import com.demo.vo.ResultVo;

public class ResultVOUtil {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("success");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo fail(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg("fail");
        resultVo.setData(null);
        return resultVo;
    }
}
