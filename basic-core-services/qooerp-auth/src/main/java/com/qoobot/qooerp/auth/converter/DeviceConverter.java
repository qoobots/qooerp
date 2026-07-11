package com.qoobot.qooerp.auth.converter;

import com.qoobot.qooerp.auth.entity.AuthDevice;
import com.qoobot.qooerp.auth.vo.DeviceVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备对象转换器
 */
public class DeviceConverter {

    /**
     * Entity → VO
     */
    public static DeviceVO toVO(AuthDevice entity) {
        if (entity == null) {
            return null;
        }
        DeviceVO vo = new DeviceVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * List<Entity> → List<VO>
     */
    public static List<DeviceVO> toVOList(List<AuthDevice> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        return entities.stream()
                .map(DeviceConverter::toVO)
                .collect(Collectors.toList());
    }
}
