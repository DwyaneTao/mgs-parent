package com.mgs.finance.lock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.lock.mapper.FinanceLockMapper;
import com.mgs.finance.lock.service.FinanceLockService;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.response.OrderFinanceLockDTO;
import com.mgs.finance.remote.lock.response.SupplyOrderFinanceLockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FinanceLockServiceImpl implements FinanceLockService {

    @Autowired
    private FinanceLockMapper financeLockMapper;

    @Override
    public PaginationSupportDTO<OrderFinanceLockDTO> queryOrderList(QueryOrderFinanceLockListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<OrderFinanceLockDTO> list =financeLockMapper.queryOrderList(request);
        PageInfo<OrderFinanceLockDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<OrderFinanceLockDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public Response lockOrder(FinanceLockOrderDTO request) {
        Response response=new Response();
        Integer canLock = financeLockMapper.checkOrderCanLock(request.getOrderId());
        if(canLock != null  && request.getLockStatus() == 0){
            return new Response(ResultCodeEnum.FAILURE.code, null, "已确认的非单结账单不能解锁");
        }

        financeLockMapper.lockOrder(request);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public PaginationSupportDTO<SupplyOrderFinanceLockDTO> querySupplyOrderList(QuerySupplyOrderFinanceLockListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<SupplyOrderFinanceLockDTO> list =financeLockMapper.querySupplyOrderList(request);
        PageInfo<SupplyOrderFinanceLockDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<SupplyOrderFinanceLockDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public Response lockSupplyOrder(FinanceLockSupplyOrderDTO request) {
        Response response=new Response();
        Integer canLock = financeLockMapper.checkSupplyOrderCanLock(request.getSupplyOrderId());
        if(canLock != null && request.getLockStatus() == 0){
            return new Response(ResultCodeEnum.FAILURE.code, null, "已确认的非单结账单不能解锁");
        }

        financeLockMapper.lockSupplyOrder(request);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
