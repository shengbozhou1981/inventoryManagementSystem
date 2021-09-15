package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.StockAlert;
import com.demo.service.StockAlertService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-28
 */
@RestController
@RequestMapping("//stockAlert")
public class StockAlertController {
    @Autowired
    private StockAlertService stockAlertService;
    @GetMapping("/list")
    public ResponseEntity<ResultVo> list(){
        List<StockAlert> stockAlerts = this.stockAlertService.list();
        if (!stockAlerts.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(stockAlerts));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/listFromDate/{date}")
    public ResponseEntity<ResultVo> listFromDate(@PathVariable("date") String date){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ge("create_time",date);
        queryWrapper.orderByDesc("create_time");
        List<StockAlert> stockAlerts = this.stockAlertService.list(queryWrapper);
        if (!stockAlerts.isEmpty())  return ResponseEntity.ok(ResultVOUtil.success(stockAlerts));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/listByDate/{date}")
    public ResponseEntity<ResultVo> listByDate(@PathVariable("date") String date){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("create_time",date);
        List<StockAlert> stockAlerts = this.stockAlertService.list(queryWrapper);
        if (!stockAlerts.isEmpty())  return ResponseEntity.ok(ResultVOUtil.success(stockAlerts));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }
}

