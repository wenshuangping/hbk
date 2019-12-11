

package com.github.common.vo;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author wsp
 * @date 2017-12-18
 */
@Data
public class ImageCode implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String code;

    private LocalDateTime expireTime;

    private BufferedImage image;

    public ImageCode(BufferedImage image, String sRand, int defaultImageExpire) {
        this.image = image;
        this.code = sRand;
        this.expireTime = LocalDateTime.now().plusSeconds(defaultImageExpire);
    }
}
