[![](https://jitpack.io/v/wkxjc/ShareManager.svg)](https://jitpack.io/#wkxjc/ShareManager)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# ShareManager [English Doc](https://github.com/wkxjc/ShareManager/blob/master/README.MD) | 中文文档

这是一个帮助添加Facebook、Twitter分享，邮件、短信分享的库。

## 屏幕截图

<img src="screenshots/login.gif" width=25%><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><img src="screenshots/shareByFacebook.gif" width=25%><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><img src="screenshots/shareLocalByFacebook.gif" width=25%>
<img src="screenshots/shareByTwitter.gif" width=25%><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><img src="screenshots/sendEmail.gif" width=25%><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><img src="screenshots/sendSms.gif" width=25%>

## 下载

第一步. 在根目录的 build.gradle 中添加:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

第二步. 在app模块的build.gradle中添加

```
dependencies {
        implementation 'com.github.wkxjc:ShareManager:1.0.3'
        // 确保添加了 support-design 库, 你可以换成自己使用的版本
        implementation 'com.android.support:design:28.0.0'
        // 确保添加了 rxjava2 库, 你可以换成自己使用的版本
        implementation 'io.reactivex.rxjava2:rxjava:2.2.7'
}
```

## 怎么使用

Facebook、Twitter分享，邮件、短信分享仅需一行代码:

```
/**facebook分享文字*/
btn_share_text_by_facebook.setOnClickListener {
    shareText(
            ShareConstants.FACEBOOK,
            "This manager is as steady as an old dog!"
    )
}
/**facebook分享链接*/
btn_share_link_by_facebook.setOnClickListener {
    shareLink(
            ShareConstants.FACEBOOK,
            "https://github.com/wkxjc/ShareManager",
            "#ShareManager",
            "This manager is as steady as an old dog!"
    )
}
/**facebook分享bitmap图片*/
btn_share_bitmap_by_facebook.setOnClickListener {
    shareImage(
            ShareConstants.FACEBOOK,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
            "#ShareManager"
    )
}
/**facebook分享本地图片*/
btn_share_local_image_by_facebook.setOnClickListener {
    shareImage(
            ShareConstants.FACEBOOK,
            localImageUri,
            "#ShareManager"
    )
}
/**facebook分享本地视频*/
btn_share_video_by_facebook.setOnClickListener {
    shareVideo(
            ShareConstants.FACEBOOK,
            localVideoUri,
            "#ShareManager"
    )
}
/**facebook分享多张图片、视频*/
btn_share_media_by_facebook.setOnClickListener {
    shareMedia(
            ShareConstants.FACEBOOK,
            listOf(
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            ),
            listOf(localVideoUri, localVideoUri, localVideoUri),
            "#ShareManager"
    )
}
/**twitter分享文字*/
btn_share_text_by_twitter.setOnClickListener {
    shareText(
            ShareConstants.TWITTER,
            "This manager is as steady as an old dog!"
    )
}
/**twitter分享本地图片*/
btn_share_local_image_by_twitter.setOnClickListener {
    shareImage(
            ShareConstants.TWITTER,
            localImageUri,
            "#ShareManager"
    )
}
/**邮件发送文字*/
btn_send_text_email.setOnClickListener {
    sendEmail(
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
/**邮件发送图片*/
btn_send_image_email.setOnClickListener {
    if (!checkLocalImageUri()) return@setOnClickListener
    sendImageEmail(
        localImageUri,
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
/**邮件发送视频*/
btn_send_video_email.setOnClickListener {
    if (!checkLocalVideoUri()) return@setOnClickListener
    sendVideoEmail(
        localVideoUri,
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
/**邮件发送多媒体*/
btn_send_media_email.setOnClickListener {
    sendMediaEmail(
        listOf(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round),
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        ),
        listOf(localVideoUri),
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
/**SMS分享*/
btn_send_sms.setOnClickListener {
    sendSMS(
            "This manager is as steady as an old dog!",
            "10086"
    )
```

## 配置

在这之前，你需要做一些准备工作.

第一步. 如果你需要使用Facebook登录，将以下代码添加到你的AndroidManifest:
```
<provider
    android:name="com.facebook.FacebookContentProvider"
    android:authorities="com.facebook.app.FacebookContentProvider你的FACEBOOK_APP_ID"
    android:exported="true"/>
```
将provider中 "你的FACEBOOK_APP_ID" 替换为你的app的 facebook_app_id.

第二步. Twitter分享必须在用户使用Twitter登录之后. 所以如果你需要使用Twitter分享，首先确保用户已经使用了Twitter登录.
我推荐你使用我的 [LoginManager](https://github.com/wkxjc/LoginManager) 库. 它和这个ShareManager库一样方便.
如果你不需要使用Twitter分享，当我没说。

第三步. Twitter分享本地图片需要 READ_EXTERNAL_STORAGE 权限, 在使用此功能之前确保你已经获得此权限.

## 如何获取 facebook_app_id?

你可以在 [LoginManager](https://github.com/wkxjc/LoginManager) 库中找到获取facebook_app_id的步骤.

## 不希望继承 BaseShareActivity?

你可以仿照 BaseShareActivity, 直接使用 ShareManager:

```
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
```

## 不希望添加全部的分享方式？

你可以单独使用其中任何一个功能。

## Bug 反馈

如果您有任何反馈或建议，欢迎提交到 [Github issues](https://github.com/wkxjc/ShareManager/issues).

## LICENSE

    Copyright (c) 2019-present, Alpinist Wang Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.