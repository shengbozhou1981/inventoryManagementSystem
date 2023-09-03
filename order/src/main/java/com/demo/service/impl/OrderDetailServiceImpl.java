package com.demo.service.impl;

import com.demo.entity.OrderDetail;
import com.demo.entity.SalesReport;
import com.demo.mapper.OrderDetailMapper;
import com.demo.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.service.SalesReportService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.SalesStatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private SalesReportService salesReportService;
    @Override
    public List<SalesStatisticVo> salesReport(Integer days) {
        List<SalesStatisticVo> salesStatisticVoList = this.orderDetailMapper.salesReport(days);
        if (!salesStatisticVoList.isEmpty()) {
            SalesReport salesReport = new SalesReport();
            salesReport.setReportDetail(salesStatisticVoList.toString());
            salesReport.setReportPastDays(days);
            try {
                this.salesReportService.save(salesReport);
            } catch (Exception e) {
                log.info("sales report save fails, please check");
                e.printStackTrace();
            }

        }
        return salesStatisticVoList;
    }
}
