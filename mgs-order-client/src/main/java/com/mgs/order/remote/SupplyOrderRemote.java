package com.mgs.order.remote;

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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value = "mgs-order-server")
public interface SupplyOrderRemote {

    /**
     * 修改产品
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplyProduct")
    Response modifySupplyProduct(@RequestBody ModifySupplyProductDTO request);

    /**
     * 添加产品
     * @param request
     * @return
     */
    @PostMapping("/order/addProduct")
    Response addProduct(@RequestBody AddProductDTO request);

    /**
     * 删除产品
     * @param request
     * @return
     */
    @PostMapping("/order/deleteProduct")
    Response deleteProduct(@RequestBody SupplyProductIdDTO request);

    /**
     * 发单
     * @param request
     * @return
     */
    @PostMapping("/order/sendToSupplier")
    Response sendToSupplier(@RequestBody SendToSupplierDTO request);

    /**
     * 保存供货单结果
     * @param request
     * @return
     */
    @PostMapping("/order/saveSupplyResult")
    Response saveSupplyResult(@RequestBody SaveSupplyResultDTO request);

    /**
     * 修改供货单结算方式
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplyOrderSettlementType")
    Response modifySupplyOrderSettlementType(@RequestBody ModifySupplyOrderSettlementTypeDTO request);

    /**
     * 上传供货单附件
     * @param request
     * @return
     */
    @PostMapping("/order/saveSupplyAttachment")
    Response saveSupplyAttachment(@RequestBody SaveSupplyAttachmentDTO request);
    /**
     * 删除供货单附件
     * @param request
     * @return
     */
    @PostMapping("/order/deleteSupplyAttachment")
    Response deleteSupplyAttachment(@RequestBody SupplyAttachmentIdDTO request);

    /**
     * 修改供应商订单号
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplierOrderCode")
    Response modifySupplierOrderCode(@RequestBody ModifySupplierOrderCodeDTO request);

    /**
     * 批量打印供货单
     * @param request
     * @return
     */
    @PostMapping("/order/batchPrintSupplierOrder")
    Response batchPrintSupplierOrder(@RequestBody Map<String,String> request);
}
