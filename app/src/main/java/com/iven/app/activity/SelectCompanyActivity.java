package com.iven.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iven.app.R;
import com.iven.app.adapter.CommonAdapter;
import com.iven.app.adapter.ViewHolder;
import com.iven.app.base.BaseActivity;
import com.iven.app.bean.CompanyBean;
import com.iven.app.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SelectCompanyActivity extends BaseActivity {
    private static final String TAG = "zpy_SelectCompanyActivity";
    private ListView lv_company;
    private List<CompanyBean> companyes;
    private CommonAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.activity_select_company;
    }

    @Override
    public void setTitle() {
        title_title.setText("选择公司");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        lv_company = (ListView) findViewById(R.id.lv_company);
        companyes = new ArrayList<>();
        addCompany();
        lv_company.setAdapter(mAdapter = new CommonAdapter<CompanyBean>(SelectCompanyActivity.this, companyes, R.layout.layout_item_company) {
            @Override
            public void convert(ViewHolder helper, CompanyBean item) {
                helper.setText(R.id.tv_com_name, item.getName());
            }

        });
        lv_company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyBean companyBean = companyes.get(position);
                transInfo(companyBean);
            }
        });
    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

    /**
     * 回传信息
     */
    private void transInfo(CompanyBean companyBean) {
        Intent intent = new Intent();
        intent.putExtra(Constant.FLAG_COMPANY, companyBean);
        setResult(Constant.RESULT_CODE, intent);
        SelectCompanyActivity.this.finish();
    }

    /*    @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent();
                intent.putExtra(Constant.FLAG_COMPANY, "shunfeng");
                setResult(Constant.RESULT_CODE, intent);
                finish();
            }
            return super.onKeyDown(keyCode, event);
        }*/
    private void addCompany() {
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("天天快递").setCode("tiantian"));
        companyes.add(new CompanyBean().setName("宅急送").setCode("zhaijisong"));
        companyes.add(new CompanyBean().setName("韵达快递").setCode("yunda"));
        companyes.add(new CompanyBean().setName("中通").setCode("zhongtong"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
        companyes.add(new CompanyBean().setName("百世快递").setCode("baishiwuliu"));
    }
}
