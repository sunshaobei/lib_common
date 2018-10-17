package com.sunsh.baselibrary.widgets.pickview.citypickerview.Interface;


import com.sunsh.baselibrary.widgets.pickview.citypickerview.bean.CityBean;
import com.sunsh.baselibrary.widgets.pickview.citypickerview.bean.DistrictBean;
import com.sunsh.baselibrary.widgets.pickview.citypickerview.bean.ProvinceBean;


public abstract class OnCityItemClickListener {
    
    /**
     * 当选择省市区三级选择器时，需要覆盖此方法
     * @param province
     * @param city
     * @param district
     */
    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
        
    }
    
    /**
     * 取消
     */
    public void onCancel() {
        
    }
}
