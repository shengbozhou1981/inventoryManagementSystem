package com.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderHead implements Serializable {

    private static final long serialVersionUID = 1L;

//    private Integer id;
@TableId(type = IdType.ASSIGN_UUID)
    private String orderId;

    private String customerName;

    private Integer terminalId;

    private BigDecimal orderAmount;

    private String orderStatus;

    private String paymentMethod;

    private String payStatus;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime updateTime;


}
