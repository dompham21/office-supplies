package com.luv2code.shopme.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderStatus implements Serializable {
    private Integer id;
    private String name;

    private static List<OrderStatus> orderStatusList;
    public OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    static {
        orderStatusList = new ArrayList<>();
        orderStatusList.add(new OrderStatus(1, "Chờ xác nhận"));
        orderStatusList.add(new OrderStatus(2, "Chờ lấy hàng"));
        orderStatusList.add(new OrderStatus(3, "Đang giao"));
        orderStatusList.add(new OrderStatus(4, "Đã giao"));
        orderStatusList.add(new OrderStatus(5, "Đã hủy"));
    }
    public static List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public static OrderStatus getOrderStatusById(Integer id) {
        for(OrderStatus carnet : orderStatusList) {
            if(carnet.getId().equals(id)) {
                return carnet;
            }
        }
        return null;
    }

    public static Integer getPositionOrderStatus(OrderStatus orderStatus) {
        return orderStatusList.indexOf(orderStatus);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
