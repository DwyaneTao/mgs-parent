package com.mgs.controller.finance.statement;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.statement.request.AddStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.CreateAgentStatementDTO;
import com.mgs.finance.remote.statement.request.DeleteStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.ModifySettlementDateDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementNameDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.NotifyCollectionOfStatementDTO;
import com.mgs.finance.remote.statement.request.NotifyPaymentOfStatementDTO;
import com.mgs.finance.remote.statement.request.QueryAgentStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutAgentListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.AgentStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.AgentStatementDetailDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.response.UncheckOutAgentDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementOrderDTO;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/test/finance/agent")
public class TestAgentStatementController {

    /**
     * 已出账单查询
     */
    @RequestMapping(value = "/queryStatementList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementList(@RequestBody QueryAgentStatementListDTO request) {
        List<AgentStatementListResponseDTO> agentStatementListResponseDTOList=new ArrayList<>();
        for (int i=0;i<15;){
            agentStatementListResponseDTOList.add(new AgentStatementListResponseDTO(
                    i,
                    "B1805240000"+i,
                    "深圳环球旅行",
                    "环球3月份账单",
                    BigDecimal.valueOf(5710l),
                    BigDecimal.valueOf(5090l),
                    BigDecimal.valueOf(620l),
                    1,
                    i%4,
                    DateUtil.stringToDate("2019-06-24"),
                    20+i,
                    i%2
            ));
            agentStatementListResponseDTOList.add(new AgentStatementListResponseDTO(
                    i+1,
                    "B1805240000"+(i+1),
                    "港澳国旅",
                    "港澳3月份账单",
                    BigDecimal.valueOf(5290l),
                    BigDecimal.valueOf(4090l),
                    BigDecimal.valueOf(1220l),
                    1,
                    i%4,
                    DateUtil.stringToDate("2019-06-25"),
                    20+i,
                    i%2
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(agentStatementListResponseDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 未出账查询
     */
    @RequestMapping(value = "/queryUncheckOutAgentList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUncheckOutAgentList(@RequestBody QueryUncheckOutAgentListDTO request) {
        List<UncheckOutAgentDTO> uncheckOutAgentDTOList=new ArrayList<>();
        for (int i=0;i<15;) {
            uncheckOutAgentDTOList.add(new UncheckOutAgentDTO(
                    "A212121",
                    "春秋旅游商",
                    124,
                    BigDecimal.valueOf(28888l),
                    1
            ));
            uncheckOutAgentDTOList.add(new UncheckOutAgentDTO(
                    "A212122",
                    "环球旅游行家",
                    23,
                    BigDecimal.valueOf(1888l),
                    1
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(uncheckOutAgentDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 创建账单
     */
    @RequestMapping(value = "/createStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response createStatement(@RequestBody CreateAgentStatementDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel("B18052400001");
        return response;
    }

    /**
     * 账单详情
     */
    @RequestMapping(value = "/queryStatementDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementDetail(@RequestBody StatementIdDTO request) {
        /*AgentStatementDetailDTO statementDetailDTO=new AgentStatementDetailDTO(
                1,
                "B18052400001",
                1,
                "深圳环球旅行",
                "李明",
                "13245677894",
                "环球3月份账单",
                "李小红",
                new Date(),
                1,
                BigDecimal.valueOf(1800l),
                0,
                BigDecimal.valueOf(1000l),
                BigDecimal.valueOf(800l),
                BigDecimal.valueOf(100l),
                BigDecimal.valueOf(200l),
                DateUtil.stringToDate("2019-06-24"),
                20
        );*/
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        //response.setModel(statementDetailDTO);
        return response;
    }

//    /**
//     * 账单明细
//     */
//    @RequestMapping(value = "/queryStatementOrderList",produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public Response queryStatementOrderList(@RequestBody QueryStatementOrderListDTO request) {
//        List<StatementOrderDTO> statementOrderDTOList=new ArrayList<>();
//        for (int i=0;i<15;) {
//            statementOrderDTOList.add(new StatementOrderDTO(
//                    1,
//                   "H1805240000"+i,
//                    new Date(),
//                    "深圳喜马拉雅大酒店",
//                    "大床房",
//                    DateUtil.stringToDate("2019-06-20"),
//                    DateUtil.stringToDate("2019-06-21"),
//                    "李雷、韩梅梅",
//                    2,
//                    BigDecimal.valueOf(288l),
//                    1,
//                    1
//            ));
//            statementOrderDTOList.add(new StatementOrderDTO(
//                    1,
//                    "H1805240000"+(i+1),
//                    new Date(),
//                    "麗枫酒店（清远金碧湾店",
//                    "大床房",
//                    DateUtil.stringToDate("2019-06-25"),
//                    DateUtil.stringToDate("2019-06-26"),
//                    "王大明",
//                    1,
//                    BigDecimal.valueOf(18888l),
//                    1,
//                    1
//            ));
//            i+=2;
//        }
//        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
//        paginationSupportDTO.setTotalCount(16);
//        paginationSupportDTO.setTotalPage(2);
//        paginationSupportDTO.setItemList(statementOrderDTOList);
//        Response response=new Response(ResultCodeEnum.SUCCESS.code);
//        response.setModel(paginationSupportDTO);
//        return response;
//    }

    /**
     * 未出账订单查询
     */
    @RequestMapping(value = "/queryUnCheckOutOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUnCheckOutOrder(@RequestBody QueryUnCheckOutOrderDTO request) {
        List<UnCheckOutOrderDTO> unCheckOutOrderDTOList=new ArrayList<>();
        for (int i=0;i<15;) {
            unCheckOutOrderDTOList.add(new UnCheckOutOrderDTO(
                    i,
                    "H1805240000"+i,
                    new Date(),
                    "深圳喜马拉雅大酒店",
                    "大床房",
                    DateUtil.stringToDate("2019-06-20"),
                    DateUtil.stringToDate("2019-06-21"),
                    "李雷、韩梅梅",
                    2,
                    BigDecimal.valueOf(288l),
                    1,
                    1
            ));
            unCheckOutOrderDTOList.add(new UnCheckOutOrderDTO(
                    i+1,
                    "H1805240000"+(i+1),
                    new Date(),
                    "麗枫酒店（清远金碧湾店",
                    "大床房",
                    DateUtil.stringToDate("2019-06-25"),
                    DateUtil.stringToDate("2019-06-26"),
                    "王大明",
                    1,
                    BigDecimal.valueOf(18888l),
                    1,
                    1
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(unCheckOutOrderDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 添加账单明细
     */
    @RequestMapping(value = "/addStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addStatementOrderList(@RequestBody AddStatementOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 删除账单明细
     */
    @RequestMapping(value = "/deleteStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改账单状态
     */
    @RequestMapping(value = "/modifyStatementStatus",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 导出账单
     */
    @RequestMapping(value = "/exportStatement")
    public void exportAgentBillImport(StatementIdDTO requestDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    /**
     * 账单明细变更查询
     */
    @RequestMapping(value = "/queryUpdatedStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request) {
        List<UpdatedStatementOrderDTO> updatedStatementOrderDTOList=new ArrayList<>();
        for (int i=0;i<15;) {
            updatedStatementOrderDTOList.add(new UpdatedStatementOrderDTO(
                    "H1805240000"+i,
                    "深圳喜马拉雅大酒店",
                    "大床房",
                    "李明",
                    "将房型修改为“大床房”"
            ));
            updatedStatementOrderDTOList.add(new UpdatedStatementOrderDTO(
                    "H1805240000"+(i+1),
                    "麗枫酒店（清远金碧湾店）",
                    "大床房",
                    "李明",
                    "将订单金额修改为“180”"
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(updatedStatementOrderDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 更新账单明细
     */
    @RequestMapping(value = "/updateStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response updateStatementOrderList(@RequestBody StatementIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改账单名称
     */
    @RequestMapping(value = "/modifyStatementName",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyStatementName(@RequestBody ModifyStatementNameDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改结算日期
     */
    @RequestMapping(value = "/modifySettlementDate",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
