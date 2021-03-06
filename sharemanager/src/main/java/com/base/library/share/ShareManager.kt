package com.base.library.share

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.base.library.share.common.constants.ShareConstants
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.email.EmailShareManager
import com.base.library.share.facebook.FacebookShareManager
import com.base.library.share.sms.SMSShareManager
import com.base.library.share.twitter.TwitterShareManager


/**
 * Description:
 * 分享管理类
 *
 * 1.Facebook分享：支持facebook分享文字、链接、图片、视频、多媒体
 *  facebook分享链接时，通过tag添加文字，通过quote添加引文
 *  facebook只支持分享本地视频
 *  facebook分享多媒体时，图片+视频总数最多6个
 *  分享图片、视频、多媒体时，可以通过 tag 添加文字
 *
 * 2.Twitter分享：支持Twitter分享文字、图片
 *  分享图片时，可以通过 tag 添加文字
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareManager {
    private var facebookShareManager: FacebookShareManager? = null
    private var twitterShareManager: TwitterShareManager? = null
    private var emailShareManager: EmailShareManager? = null
    private var smsShareManager: SMSShareManager? = null
    private var activity: Activity
    private var onShareListener: OnShareListener

    constructor(activity: Activity, onShareListener: OnShareListener) {
        this.activity = activity
        this.onShareListener = onShareListener
    }

    constructor(fragment: Fragment, onShareListener: OnShareListener) {
        val activity = fragment.activity ?: throw NullPointerException("fragment.activity is null")
        this.activity = activity
        this.onShareListener = onShareListener
    }

    private fun getFacebookShareManager(): FacebookShareManager? {
        if (facebookShareManager == null)
            facebookShareManager = FacebookShareManager(activity, onShareListener)
        return facebookShareManager
    }

    private fun getTwitterShareManager(): TwitterShareManager? {
        if (twitterShareManager == null)
            twitterShareManager = TwitterShareManager(activity, onShareListener)
        return twitterShareManager
    }

    private fun getEmailShareManager(): EmailShareManager? {
        if (emailShareManager == null)
            emailShareManager = EmailShareManager(activity, onShareListener)
        return emailShareManager
    }

    private fun getSMSShareManager(): SMSShareManager? {
        if (smsShareManager == null)
            smsShareManager = SMSShareManager(activity, onShareListener)
        return smsShareManager
    }

    fun shareText(type: String, text: String) {
        when (type) {
            ShareConstants.FACEBOOK -> getFacebookShareManager()?.shareText(text)
            ShareConstants.TWITTER -> getTwitterShareManager()?.shareText(text)
        }
    }

    fun shareLink(type: String, link: String, tag: String, quote: String) {
        when (type) {
            ShareConstants.FACEBOOK -> getFacebookShareManager()?.shareLink(link, tag, quote)
            ShareConstants.TWITTER -> onShareListener.onShareFail(
                ShareConstants.TWITTER,
                "Twitter share does not support link yet"
            )
        }
    }

    fun shareImage(type: String, image: Any?, tag: String = "") {
        when (type) {
            ShareConstants.FACEBOOK -> getFacebookShareManager()?.shareImage(image, tag)
            ShareConstants.TWITTER -> getTwitterShareManager()?.shareImage(image, tag)
        }
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        when (type) {
            ShareConstants.FACEBOOK -> getFacebookShareManager()?.shareVideo(videoUri, tag)
            ShareConstants.TWITTER -> onShareListener.onShareFail(
                ShareConstants.TWITTER,
                "Twitter share does not support video yet"
            )
        }
    }

    fun shareMedia(type: String, imageList: List<Any>, videoUriList: List<Uri>, tag: String) {
        when (type) {
            ShareConstants.FACEBOOK -> getFacebookShareManager()?.shareMedia(imageList, videoUriList, tag)
            ShareConstants.TWITTER -> onShareListener.onShareFail(
                ShareConstants.TWITTER,
                "Twitter share does not support media yet"
            )
        }
    }

    fun sendEmail(
        emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        getEmailShareManager()?.sendTextEmail(emailBody, emailSubject, tos, ccs)
    }

    fun sendImageEmail(
        image: Any = Uri.EMPTY, emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        getEmailShareManager()?.sendImageEmail(image, emailBody, emailSubject, tos, ccs)
    }

    fun sendVideoEmail(
        video: Uri = Uri.EMPTY, emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        getEmailShareManager()?.sendVideoEmail(video, emailBody, emailSubject, tos, ccs)
    }

    fun sendMediaEmail(
        imageList: List<Any> = ArrayList(),
        videoList: List<Uri> = ArrayList(),
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        getEmailShareManager()?.sendMediaEmail(imageList, videoList, emailBody, emailSubject, tos, ccs)
    }

    fun sendSMS(smsBody: String = "", phoneNumber: String = "") {
        getSMSShareManager()?.sendSMS(smsBody, phoneNumber)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager?.handleActivityResult(requestCode, resultCode, data)
    }

    fun release() {
        twitterShareManager?.release()
    }

}