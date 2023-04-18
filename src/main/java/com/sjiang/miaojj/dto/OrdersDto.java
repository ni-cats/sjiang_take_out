package com.sjiang.miaojj.dto;

import com.sjiang.miaojj.entity.OrderDetail;
import com.sjiang.miaojj.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
