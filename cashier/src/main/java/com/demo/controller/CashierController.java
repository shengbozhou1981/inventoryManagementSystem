package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.Cashier;
import com.demo.service.CashierService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@RestController
@RequestMapping("//cashier")
public class CashierController {
    @Autowired
    private CashierService cashierService;

    @GetMapping("/list")
    public ResponseEntity<ResultVo> list() {
        List<Cashier> cashierList = this.cashierService.list();
        if (!cashierList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(cashierList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    //    Find an cashier by ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<ResultVo> findById(@PathVariable("id") String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("terminal_id", id);
        Cashier cashier = cashierService.getOne(queryWrapper);
        if (cashier != null) return ResponseEntity.ok(ResultVOUtil.success(cashier));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultVOUtil.fail());
    }

    //Create a cashier terminal
    @PostMapping("/create/{terminalId}/{token}")
    public ResponseEntity<ResultVo> create(@PathVariable("terminalId") Integer terminalId, @PathVariable("token") String token) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.cashierService.insert(terminalId, token));

    }

    //    test distributed transaction
    @PostMapping("/insert/{terminalId}/{token}")
    public String insert(@PathVariable("terminalId") Integer terminalId, @PathVariable("token") String token) {

        return this.cashierService.insert(terminalId, token).toString();

    }
}

