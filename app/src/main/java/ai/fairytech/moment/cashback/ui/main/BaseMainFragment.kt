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

package ai.fairytech.moment.cashback.ui.main

import ai.fairytech.moment.MomentSDK
import ai.fairytech.moment.cashback.R
import ai.fairytech.moment.exception.MomentException
import ai.fairytech.moment.cashback.databinding.FragmentMainBinding
import ai.fairytech.moment.cashback.notification.NotificationController
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * 앱 시작시 구동되는 첫 Fragment.
 * 기능:
 *  - 권한 허용 및 서비스 시작
 *  - 캐시백 프로그램 리스트 제공
 *  - 마이페이지로 이동.
 */
open class BaseMainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    protected val binding get() = _binding!!

    protected lateinit var moment: MomentSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moment = MomentSDK.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext().applicationContext
        // service가 os에 의해 종료됐거나, 사용자가 강제종료했을 수도 있으므로 restart를 시도.
        moment.restartIfNeeded(getConfig(context), object :
            MomentSDK.RestartResultCallback {
            override fun onSuccess(resultCode: MomentSDK.RestartResultCode) {
                if (resultCode == MomentSDK.RestartResultCode.SERVICE_RESTARTED) {
                    Toast.makeText(
                        context,
                        "restart에 성공했습니다: $resultCode",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(exception: MomentException) {
                Toast.makeText(context, "restart에 실패했습니다.", Toast.LENGTH_SHORT).show()
                Log.e(
                    "MomentSDK",
                    "restartIfNeeded onFailure(${exception.errorCode.name}): ${exception.message}"
                )
            }
        })
        moment.setUserId("test-user-id")

        // 서비스를 시작하는 스위치
        binding.textCashbackService.setOnClickListener {
            handleStart()
        }
    }

    private fun getConfig(context: Context): MomentSDK.Config {
        return MomentSDK.Config(context)
            .notificationChannelId(NotificationController.NOTIFICATION_CHANNEL_ID) // 알림 채널 아이디
            .notificationId(NotificationController.NOTIFICATION_ID) // 알림 아이디
            .notificationIconResId(R.drawable.baseline_person_24)
            .serviceNotificationChannelId(NotificationController.SERVICE_NOTIFICATION_CHANNEL_ID) // 서비스를 위해 필요한 채널아이디
            .serviceNotificationId(NotificationController.SERVICE_NOTIFICATION_ID)
            .serviceNotificationIconResId(R.drawable.baseline_person_24)
            .serviceNotificationTitle("인식 서비스")
            .serviceNotificationText("인식 서비스가 동작 중입니다")
    }

    // 서비스 시작
    private fun handleStart() {
        try {
            val context = requireContext().applicationContext
            moment.setMarketingPushEnabled(true)
            moment.setSendBubble(false)
            moment.launchUI(getConfig(context), object : MomentSDK.ResultCallback {
                override fun onSuccess() {
                    Toast.makeText(context, "launch에 성공했습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(exception: MomentException) {
                    Toast.makeText(context, "launch에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.e(
                        "MomentSDK",
                        "start onFailure(${exception.errorCode.name}): ${exception.message}"
                    )
                }
            })
        } catch (e: MomentException) {
            Toast.makeText(context, "launch에 실패했습니다.", Toast.LENGTH_SHORT).show()
            Log.e(
                "MomentSDK",
                "start onFailure(${e.errorCode.name}): ${e.message}"
            )
        }
    }
}