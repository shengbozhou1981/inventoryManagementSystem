package com.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.Cashier;
import com.demo.mapper.CashierMapper;
import com.demo.service.CashierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@Service
@Slf4j
public class CashierServiceImpl extends ServiceImpl<CashierMapper, Cashier> implements CashierService {
    @Autowired
    private CashierMapper cashierMapper;

    @Override
    @Transactional
    public ResultVo insert(Integer terminalId, String token) {
        Cashier cashier = new Cashier();
        cashier.setTerminalId(terminalId);
        cashier.setToken(token);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("terminal_id", terminalId);

        if (this.cashierMapper.selectOne(queryWrapper) == null) {
            int insertResult = this.cashierMapper.insert(cashier);
            if (insertResult == 1) {
                return ResultVOUtil.success("create cashier successfully");
            }
        } else {
            log.info("terminal id already exist, please try another one");

            throw new RuntimeException("terminal id already exist, please try another one");
        }
        return ResultVOUtil.fail();
    }
}
