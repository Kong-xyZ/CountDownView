# CountDownView
**简介**

这是一个渐变加载进度条，可以用来做时间倒计时，中间可添加文字。

**效果预览**

![png](https://github.com/Kong-xyZ/CountDownView/blob/master/img/effect.png)
![gif](https://github.com/Kong-xyZ/CountDownView/blob/master/img/view.gif)

**使用方法**

1.在XML布局中添加控件

```xml
    <xyz.kongzz.countdownview.ProgressRing
        android:id="@+id/pr_progress"
        android:layout_width="180dp"
        android:layout_height="180dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:pr_bg_end_color="@color/bg_ring" // 设置背景结束颜色
        app:pr_bg_mid_color="@color/bg_ring" // 设置背景中间颜色
        app:pr_bg_start_color="@color/bg_ring" // 设置背景开始颜色
        app:pr_progress_end_color="#FAB528" // 设置进度条结束颜色
        app:pr_progress_start_color="#FCD057" // 设置进度条开始颜色
        app:pr_progress_width="10dp" // 设置进度条的宽度
        tools:pr_progress="80"
        tools:pr_show_text="60S" />
```

**具体属性介绍**

```xml
<!--进度起始色-->
        <attr name="pr_progress_start_color" format="color" />
        <!--进度结束色-->
        <attr name="pr_progress_end_color" format="color" />
        <!--背景起始色-->
        <attr name="pr_bg_start_color" format="color" />
        <!--背景中间色-->
        <attr name="pr_bg_mid_color" format="color" />
        <!--背景结束色-->
        <attr name="pr_bg_end_color" format="color" />
        <!--进度值 介于0-100-->
        <attr name="pr_progress" format="integer" />
        <!--进度宽度-->
        <attr name="pr_progress_width" format="dimension" />
        <!--起始角度-->
        <attr name="pr_start_angle" format="integer" />
        <!--扫过的角度-->
        <attr name="pr_sweep_angle" format="integer" />
        <!--是否显示动画-->
        <attr name="pr_show_anim" format="boolean" />
        <!--环内文字-->
        <attr name="pr_show_text" format="string" />
```

2.在代码中使用

```java
    mProgressRing.setText(mProgressSecond + "S");
    mProgressRing.setProgress((float) (mProgressSecond * 10 / 6));
```

通过这两个接口对View进行参数设置。
如果要进度条走的更加丝滑，可以对setProgress中的值更为精细，0.0~100.0（精确度到0.1)即可。