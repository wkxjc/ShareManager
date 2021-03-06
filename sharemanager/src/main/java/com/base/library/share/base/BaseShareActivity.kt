package com.base.library.share.base

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.base.library.share.ShareManager
import com.base.library.share.common.listener.OnShareListener

/**
 * Description:
 * 分享基类，可以继承此类实现分享，或者仿照此类中的方法调用ShareManager
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
abstract class BaseShareActivity : AppCompatActivity(), OnShareListener {

    private val shareManager by lazy { ShareManager(this, this) }

    fun shareText(type: String, text: String) {
        shareManager.shareText(type, text)
    }

    fun shareLink(type: String, link: String, tag: String = "", quote: String = "") {
        shareManager.shareLink(type, link, tag, quote)
    }

    fun shareImage(type: String, image: Any?, tag: String = "") {
        shareManager.shareImage(type, image, tag)
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        shareManager.shareVideo(type, videoUri, tag)
    }

    fun shareMedia(type: String, imageList: List<Any>, videoUriList: List<Uri>, tag: String = "") {
        shareManager.shareMedia(type, imageList, videoUriList, tag)
    }

    fun sendEmail(
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendEmail(emailBody, emailSubject, tos, ccs)
    }

    fun sendImageEmail(
        image: Any,
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendImageEmail(image, emailBody, emailSubject, tos, ccs)
    }

    fun sendVideoEmail(
        video: Uri,
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendVideoEmail(video, emailBody, emailSubject, tos, ccs)
    }

    fun sendMediaEmail(
        imageList: List<Any> = ArrayList(),
        videoList: List<Uri> = ArrayList(),
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendMediaEmail(imageList, videoList, emailBody, emailSubject, tos, ccs)
    }

    fun sendSMS(smsBody: String = "", phoneNumber: String = "") {
        shareManager.sendSMS(smsBody, phoneNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        shareManager.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            shareManager.release()
        }
    }
}