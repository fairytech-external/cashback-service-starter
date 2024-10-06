/*
 * Fairy Technologies CONFIDENTIAL
 * __________________
 *
 * Copyright (C) Fairy Technologies, Inc - All Rights Reserved
 *
 * NOTICE:  All information contained herein is, and remains the property of Fairy
 * Technologies Incorporated and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Fairy Technologies Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information,or reproduction or modification of this material
 * is strictly forbidden unless prior written permission is obtained from Fairy
 * Technologies Incorporated.
 *
 */

package ai.fairytech.moment.cashback.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtil {
    fun loadImageWithUrl(imageUrl: String?, imageView: ImageView?) {
        imageView?.scaleType = ImageView.ScaleType.FIT_CENTER
        if (!imageUrl.isNullOrEmpty()) {
            imageView?.context?.let {
                Glide.with(it)
                    .load(imageUrl)
                    .into(imageView)
            }
        }
    }

}