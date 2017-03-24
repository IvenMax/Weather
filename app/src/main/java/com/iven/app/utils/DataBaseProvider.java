package com.iven.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.iven.app.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iven
 * @date 2017/3/24 11:14
 * @Description
 */

public class DataBaseProvider {
    private SparseArray<User> datas = null;
    private Context mContext;
    private final String USER_JSON = "user_json";

    public DataBaseProvider(Context context) {
        mContext = context;
        datas = new SparseArray<>();
        list2Sparse();
    }

    /**
     * 保存
     */
    public void put(User cart) {
        datas.put(1, cart);
        commit();
    }

    /*public void put(HotWaresBean.ListBean wares) {
        User cart = convertData(wares);
        put(cart);
    }*/

    /**
     * 更新
     */
    /*public void update(User cart) {
        datas.put(cart.getId().intValue(), cart);
        commit();
    }*/

    /**
     * 删除
     */
    /*public void delete(User cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }*/

    /**
     * 读取
     */
    public List<User> getAll() {
        return getDataFromLocal();
    }

    //本地读取
    public List<User> getDataFromLocal() {
        String string = SPUtils.getString(mContext, USER_JSON);
        List<User> lists = null;
        if (!TextUtils.isEmpty(string)) {
            lists = JSONUtil.fromJson(string, new TypeToken<List<User>>() {
            }.getType());
        }
        return lists;
    }

    //存储到SP中
    private void commit() {
        List<User> shoppingCarts = sparse2List();
        SPUtils.putString(mContext, USER_JSON, JSONUtil.toJSON(shoppingCarts));
    }

    private List<User> sparse2List() {
        int size = datas.size();
        List<User> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }

    private void list2Sparse() {
       /* List<User> carts = getDataFromLocal();
        if (carts != null && carts.size() > 0) {
            for (User cart : carts) {
                datas.put(cart.getId().intValue(), cart);
            }
        }*/
    }


}
