package com.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-26
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class SalesReport implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "report_id", type = IdType.AUTO)
      private Integer reportId;

    private String reportDetail;

    private Integer reportPastDays;

      @TableField(fill = FieldFill.INSERT)
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      private LocalDateTime createTime;

      @TableField(fill = FieldFill.INSERT_UPDATE)
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      private LocalDateTime updateTime;


}
