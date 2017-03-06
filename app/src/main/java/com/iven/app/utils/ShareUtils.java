package com.iven.app.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.PopupWindow;

/**
 * 分享的面板显示
 *
 * @author JinnyZh
 *
 */
public class ShareUtils {

	private CustomShareBoard shareBoard;
	private Activity activity;

	private int which_Page = -1;


	/**
	 *
	 * @param activity 当前Activity
	 * @param which_Page 1.ISM完成期分享 2个人中心邀请好友 3其他页面 4ISM投资成功分享
	 * @param shareUrl 分享的url
	 * @param shareTitle 分享的title
	 * @param shareContent 分享的content
	 */
	public ShareUtils(Activity activity, int which_Page,String shareUrl,String shareTitle,String shareContent) {
		this.which_Page = which_Page;
		this.activity = activity;
		shareBoard = new CustomShareBoard(activity,which_Page,shareTitle,shareUrl,shareContent);
	}

	/** popupWindow弹窗消失的监听 */
	public void setDismissListener(PopupWindow.OnDismissListener OnDismissListener){
		shareBoard.setOnDismissListener(OnDismissListener);
	}

	public void show() {
		shareBoard.showAtLocation(activity.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, 0);
//		UmsAgent.onResume(GlobalContext.context, page);
	}
}
