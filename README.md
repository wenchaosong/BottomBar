### 轻量级的底部导航栏

在原项目[PagerBottomTabStrip](https://github.com/tyzlmjj/PagerBottomTabStrip) 基础上
增加了 getItem 方法，能设置对应 position 的 tab 属性

### 实现效果图

|![horizontal](/intro_img/demo.png "horizontal")|![vertical](/intro_img/demo8.png "vertical")|
|---|---|
|![Material 1](/intro_img/demo1.gif "Material 1")|![Material 2](/intro_img/demo2.gif "Material 2")|
|![Material 3](/intro_img/demo3.gif "Material 3")|![Material 4](/intro_img/demo4.gif "Material 4")|

### 自定义扩展例子

|仿京东重复刷新动画|Library中已经实现的一个最普通的效果|
|---|---|
|![PagerBottomTabStrip](/intro_img/demo9.gif "PagerBottomTabStrip")|![PagerBottomTabStrip](/intro_img/demo5.gif "PagerBottomTabStrip")|

|Demo中的例子||
|---|---|
|![PagerBottomTabStrip](/intro_img/demo7.png "PagerBottomTabStrip")|![PagerBottomTabStrip](/intro_img/demo6.png "PagerBottomTabStrip")|


### 使用

#### 布局文件中配置

xml文件
    
	<com.ms.bottombar.PageNavigationView
            android:id="@+id/tab"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_alignParentBottom="true"
            android:background="#FFF" />

#### java文件中设置

	  PageNavigationView bottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
      PageNavigationView.CustomBuilder custom = bottomTabLayout.custom();
      NavigationController build = custom
              .addItem(newItem(android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, "相机"))
              .addItem(newItem(android.R.drawable.ic_menu_compass, android.R.drawable.ic_menu_compass, "位置"))
              .addItem(newItem(android.R.drawable.ic_menu_search, android.R.drawable.ic_menu_search, "搜索"))
              .addItem(newItem(android.R.drawable.ic_menu_help, android.R.drawable.ic_menu_help, "帮助"))
              .build();
      build.setupWithViewPager(mVpContent);

这样就实现底部导航栏功能了

#### 设置条目选中的监听

     navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
                 @Override
                 public void onSelected(int index, int old) {
                     //选中时触发
                 }

                 @Override
                 public void onRepeat(int index) {
                     //重复选中时触发
                 }
             });

#### **导入方式**

    dependencies {
	        implementation 'com.ms:BottomBar:1.0.1'
	        implementation 'com.ms:BottomBar-androidx:1.0.1'
	}

