package com.pma101.lapmarket.Handel;

import com.pma101.lapmarket.models.Khachhang;
import com.pma101.lapmarket.models.Laptop;

public interface Item_Handle_KH {


        public void Delete(String id);

        public void Update(String id, Khachhang khachhangs);
}
