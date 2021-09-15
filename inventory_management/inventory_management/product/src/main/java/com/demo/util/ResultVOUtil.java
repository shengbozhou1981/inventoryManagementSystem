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

    public static ResultVo nullPointer(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg("null pointer exception");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo productException(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg("product related exception");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo exception(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg("application exception");
        resultVo.setData(data);
        return resultVo;
    }
}
