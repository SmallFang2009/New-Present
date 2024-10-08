# New Present
一个可以折腾你的好朋友的程序。

## 版本信息
v1.2.2

## 内容说明
### Windows版
运行后会强行播放一段由你指定的视频，并强制将音量开到最大，此期间用户无法退出此应用，直到视频播放完成。（当然我还是留了一手，按Ctrl+Alt+Delete可以进入后台打开任务管理器并结束进程）
如果你的好朋友不知道这个快捷键，那么TA就得被迫看完你“精心挑选”的这段视频了🤣

### Android版
可以简单理解为视频版的“一份礼物”，会强制将音量开到最大并播放视频（注意是循环播放），主要目的在于让人社死。（你也可以指定视频）

## Windows版使用方法
进入[release](https://github.com/FangZirui-E5/Cerulean/releases)界面，点击下载```release.for.Windows.zip```

下载完成后打开压缩包，进入source文件夹

将你想强制播放的视频命名为```video.mp4```，并放入source文件夹中

此时回到根目录，打开exe主程序就会开始强行播放你放入source文件夹的视频了

是不是非常的简单？😊
## Android版使用方法
**注意：以下操作仍然在电脑完成**

进入[release](https://github.com/FangZirui-E5/Cerulean/releases)界面。点击下载```release.for.Android.apk```

下载完成后将后缀改为zip，进入res文件夹

将你想强制播放的视频命名为```hW.mp4```，并替换res文件夹中的hW.mp4

重新将后缀改为apk

并按照这个教程：[对 APK 进行手动签名](https://learn.microsoft.com/zh-cn/previous-versions/xamarin/android/deploy-test/signing/manually-signing-the-apk)给这个apk安装包签名

发送到手机上，即可按照流程安装应用了

***特别提醒：我也没按照这个方法测试过Android版是否可以更改所播放的视频文件，一切都是按照我的理论知识写的，如果失败了可以在issue里面留言***

## LICENSE
[MIT LICENSE](https://github.com/FangZirui-E5/New-Present/blob/main/LICENSE)
