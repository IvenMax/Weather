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
import com.iven.app.okgo.JsonCallback;
import com.iven.app.utils.Constant;
import com.iven.app.utils.NewLoadingUtil;
import com.iven.app.utils.T;
import com.iven.app.view.LogisticsInformationView;
import com.iven.app.view.ReadOnlyEditText;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 快递100API
 * https://github.com/jokermonn/-Api/blob/master/ExpressDelivery100.md
 *
 * @author Iven
 * @date 2017/2/21 14:43
 * @Description
 */

public class LogisticFragment extends Fragment {
    private ArrayList<LogisticsData.DataBean> logisticsDataList;
    private static final String TAG = "zpy_ThirdFragment";
    private View view;
    private ImageView iv_scan, iv_into;
    private EditText et_kuaididan;
    private ReadOnlyEditText et_kuaidicompany;
    private final int REQUEST_CODE = 1023;
    private RelativeLayout rl_com;
    private Button btn_select;
    private String kd_company_code, kd_danhao;
    private LogisticsInformationView logistics_InformationView;
    private NewLoadingUtil mNewLoadingUtil;

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

        logistics_InformationView = (LogisticsInformationView) view.findViewById(R.id.logistics_InformationView);
        logisticsDataList = new ArrayList<>();
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
                LogisticFragment.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        iv_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCompanyActivity.class);
                LogisticFragment.this.startActivityForResult(intent, REQUEST_CODE);
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
                logistics_InformationView.setVisibility(View.GONE);
                http(et_kuaididan.getText().toString(), kd_company_code);
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
        mNewLoadingUtil = NewLoadingUtil.getInstance(getActivity());
        String url = "https://www.kuaidi100.com/query?type=" + type + "&postid=" + code + "&id=1&valicode=&temp=";
        kd_danhao = et_kuaididan.getText().toString();
        OkGo.get(url).execute(new JsonCallback<LogisticsData>() {
            @Override
            public void onSuccess(LogisticsData logisticsData, Call call, Response response) {
                logistics_InformationView.setVisibility(View.VISIBLE);
                List<LogisticsData.DataBean> data = logisticsData.getData();
                logisticsDataList.clear();
                logisticsDataList.addAll(data);
                logistics_InformationView.setLogisticsDataList(logisticsDataList);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.showLong(getActivity(),e.getMessage());
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                mNewLoadingUtil.startShowLoading();
            }

            @Override
            public void onAfter(LogisticsData logisticsData, Exception e) {
                super.onAfter(logisticsData, e);
                mNewLoadingUtil.stopShowLoading();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Constant.RESULT_CODE == resultCode) {

            CompanyBean company = (CompanyBean) (data.getSerializableExtra(Constant.FLAG_COMPANY));
            if (null != company) {
                Log.e(TAG, "onActivityResult: 125" + "行 = company =" + company.toString());
                et_kuaidicompany.setText(!TextUtils.isEmpty(company.getName()) ? company.getName() : "");
                kd_company_code = (!TextUtils.isEmpty(company.getCode()) ? company.getCode() : "");
            } else {
                T.showLong(getActivity(), "未选择快递公司");
            }
        }
    }
}
