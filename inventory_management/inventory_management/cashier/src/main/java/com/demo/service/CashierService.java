package com.demo.service;

import com.demo.entity.Cashier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.vo.ResultVo;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface CashierService extends IService<Cashier> {

   public ResultVo insert(Integer terminalId, String token);
}
