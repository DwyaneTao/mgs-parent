package com.mgs.order.service;

import com.mgs.common.Response;
import com.mgs.order.remote.request.AddProductDTO;
import com.mgs.order.remote.request.ModifySupplierOrderCodeDTO;
import com.mgs.order.remote.request.ModifySupplyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySupplyProductDTO;
import com.mgs.order.remote.request.SaveSupplyAttachmentDTO;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.remote.request.SendToSupplierDTO;
import com.mgs.order.remote.request.SupplyAttachmentIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.PrintSupplyOrderDTO;

import java.util.List;
import java.util.Map;

public interface SupplyOrderService {

    /**
     * 修改产品
     * @param request
     * @return
     */
    Response modifySupplyProduct(ModifySupplyProductDTO request);

    /**
     * 添加产品
     * @param request
     * @return
     */
    Response addProduct(AddProductDTO request);

    /**
     * 删除产品
     * @param request
     * @return
     */
    Response deleteProduct(SupplyProductIdDTO request);

    /**
     * 发单
     * @param request
     * @return
     */
    Response sendToSupplier(SendToSupplierDTO request);

    /**
     * 保存供货单结果
     * @param request
     * @return
     */
    Response saveSupplyResult(SaveSupplyResultDTO request);

    /**
     * 修改供货单结算方式
     * @param request
     * @return
     */
    Response modifySupplyOrderSettlementType(ModifySupplyOrderSettlementTypeDTO request);

    /**
     * 上传供货单附件
     * @param request
     * @return
     */
    Response saveSupplyAttachment(SaveSupplyAttachmentDTO request);

    /**
     * 删除供货单附件
     * @param request
     * @return
     */
    Response deleteSupplyAttachment(SupplyAttachmentIdDTO request);

    /**
     * 修改供应商订单号
     * @param request
     * @return
     */
    Response modifySupplierOrderCode(ModifySupplierOrderCodeDTO request);

    /**
     * 批量打印供货单
     * @param request
     * @return
     */
    Response batchPrintSupplierOrder(Map<String,String> request);

    }
