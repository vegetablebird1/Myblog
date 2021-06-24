package com.ming.service;

import com.ming.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 历史记录 服务类
 * </p>
 *
 * @author ming
 * @since 2021-06-23
 */
public interface HistoryService extends IService<History> {

    void incrementViews(String address);

}
