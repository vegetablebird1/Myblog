package com.ming.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 历史记录
 * </p>
 *
 * @author ming
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_history")
@ApiModel(value="History对象", description="历史记录")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "历史id")
    @TableId(value = "history_id", type = IdType.AUTO)
    private Long historyId;

    @ApiModelProperty(value = "访问次数")
    private Long viewNumber;

    @ApiModelProperty(value = "网站开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "网站结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "网站域名")
    private String domainName;


}
