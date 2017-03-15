package com.iven.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iven.app.R;
import com.iven.app.activity.SelectCompanyActivity;
import com.iven.app.bean.CompanyBean;
import com.iven.app.bean.LogisticsData;
import com.iven.app.utils.Constant;
import com.iven.app.utils.T;
import com.iven.app.view.LogisticsInformationView;
import com.iven.app.view.ReadOnlyEditText;

import java.util.ArrayList;


/**
 * 快递100API
 * https://github.com/jokermonn/-Api/blob/master/ExpressDelivery100.md
 *
 * @author Iven
 * @date 2017/2/21 14:43
 * @Description
 */

public class ThirdFragment extends Fragment {
    private ArrayList<LogisticsData.DataBean> logisticsDataList;
    private static final String TAG = "zpy_ThirdFragment";
    private View view;
    private ImageView iv_scan, iv_into;
    private EditText et_kuaididan;
    private ReadOnlyEditText et_kuaidicompany;
    private final int REQUEST_CODE = 1023;
    private RelativeLayout rl_com;
    private Button btn_select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_third, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        rl_com = (RelativeLayout) view.findViewById(R.id.rl_com);
        et_kuaididan = (EditText) view.findViewById(R.id.et_kuaididan);
        et_kuaidicompany = (ReadOnlyEditText) view.findViewById(R.id.et_kuaidicompany);
        iv_scan = ((ImageView) view.findViewById(R.id.iv_scan));
        iv_into = ((ImageView) view.findViewById(R.id.iv_into));
        btn_select = (Button) view.findViewById(R.id.btn_select);
        setListener();

        LogisticsInformationView logistics_InformationView = (LogisticsInformationView) view.findViewById(R.id.logistics_InformationView);
        logisticsDataList = new ArrayList<>();
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-20 07:23:06").setContext("[杭州市]浙江杭州江干公司派件员：吕敬桥  15555555551  正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 23:11:37").setContext("[杭州市]浙江杭州江干区新杭派公司派件员：吕敬桥  15555555552  正在为您派件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 23:08:06").setContext("[杭州市]浙江派件员：吕敬桥  15555555553  正在为您派件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 23:08:06").setContext("[杭州市]员：吕敬桥  15555555554  正在为您派件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 12:39:15").setContext("包裹正等待揽件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-20 06:48:37").setContext("到达目的地网点浙江杭州江干区新杭派，快件很快进行派送"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 23:11:37").setContext("[苏州市]江苏苏州分拨中心  已发出"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 23:08:06").setContext("[苏州市]快件已到达  江苏苏州分拨中心"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData.DataBean().setTime("2017-1-19 12:39:15").setContext("包裹正等待揽件"));
        //        logistics_InformationView.setLogisticsDataList(logisticsDataList);
    }

    private void setListener() {
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getActivity(), "扫描二维码");
            }
        });
        rl_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCompanyActivity.class);
                ThirdFragment.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        iv_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCompanyActivity.class);
                ThirdFragment.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_kuaididan.getText().toString())) {
                    T.showLong(getActivity(), "快递单号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_kuaidicompany.getText().toString())) {
                    T.showLong(getActivity(), "快递公司不能为空");
                    return;
                }
                http(et_kuaididan.getText().toString(), et_kuaidicompany.getText().toString());
            }
        });
    }

    /**
     * 网络查询物流信息
     *
     * @param code 快递单
     * @param type 快递公司
     */
    private void http(String code, String type) {
        T.showLong(getActivity(), "todo...");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Constant.RESULT_CODE == resultCode) {

            CompanyBean company = (CompanyBean) (data.getSerializableExtra(Constant.FLAG_COMPANY));
            if (null != company) {
                Log.e(TAG, "onActivityResult: 125" + "行 = company =" + company.toString());
                et_kuaidicompany.setText(!TextUtils.isEmpty(company.getName()) ? company.getName() : "");
            } else {
                T.showLong(getActivity(), "未选择快递公司");
            }
        }
    }
}
