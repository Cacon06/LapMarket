

var express = require("express");
var router = express.Router();

const Laptop = require("../models/laptop")
const KhachHang = require("../models/khachang")



const Upload = require('../config/common/upload');

router.post('/add-laptop', async (req, res) => {
  try {
    const data = req.body;

    const newLap = new Laptop({
      hinhanh: data.hinhanh,
      ten: data.ten,
      gia: data.gia,
      thuonghieu: data.thuonghieu,
      xuatxu: data.xuatxu,
    });

    const result = await newLap.save();

    if (result) {
      res.json({
        "status": 200,
        "messenger": "Thêm thành công",
        "data": result
      });
    } else {
      res.json({
        "status": 400,
        "messenger": "Lỗi, thêm không thành công",
        "data": []
      });
    }
  } catch (error) {
    console.log(error);
  }
});




router.post('/add-kh', async (req, res) => {
  try {
    const data = req.body;

    const newKH = new KhachHang({
      AnhDaiDien: data.AnhDaiDien,
      TenKH: data.TenKH,
      TuoiKH: data.TuoiKH,
      DiaChi: data.DiaChi,
      GioiTinh: data.GioiTinh,
    });

    const result = await newKH.save();

    if (result) {
      res.json({
        "status": 200,
        "messenger": "Thêm thành công",
        "data": result
      });
    } else {
      res.json({
        "status": 400,
        "messenger": "Lỗi, thêm không thành công",
        "data": []
      });
    }
  } catch (error) {
    console.log(error);
  }
});





router.get("/get-list-laptop", async (req, res) => {
  try {
    //lấy danh sách theo thứ tự nhà phân phối mới nhất
    const data = await Laptop.find().sort({ createdAt: -1 });
   
    if (data) {
      //nếu lây ds thanh công thì trả về danh sách dữ liệu
      res.json({
        status: 200,
        messenger: "Lấy danh sách thành công",
        data: data,
      });
    } else {
      res.json({
        status: 400,
        messenger: "lấy danh sách thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});



router.get("/get-list-kh", async (req, res) => {
  try {
    //lấy danh sách theo thứ tự nhà phân phối mới nhất
    const data = await KhachHang.find().sort({ createdAt: -1 });
   
    if (data) {
      //nếu lây ds thanh công thì trả về danh sách dữ liệu
      res.json({
        status: 200,
        messenger: "Lấy danh sách thành công",
        data: data,
      });
    } else {
      res.json({
        status: 400,
        messenger: "lấy danh sách thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});




router.delete("/delete-laptop-by-id/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const result = await Laptop.findByIdAndDelete(id);
    if (result) {
      res.json({
        status: 200,
        messenger: "tìm và xóa theo id thành công",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        messenger: "tìm và xóa thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});



router.delete("/delete-kh-by-id/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const result = await KhachHang.findByIdAndDelete(id);
    if (result) {
      res.json({
        status: 200,
        messenger: "tìm và xóa theo id thành công",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        messenger: "tìm và xóa thất bại",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});



//update
router.put("/update-laptop-by-id/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const data = req.body;
    const result = await Laptop.findByIdAndUpdate(id, {
      hinhanh: data.hinhanh,
      ten: data.ten,
      gia: data.gia,
      thuonghieu: data.thuonghieu,
      xuatxu: data.xuatxu,
    });
    if (result) {
      res.json({
        status: 200,
        messenger: "tìm thấy id và update thành công",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        messenger: "update thất bại",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
  }
});



router.put("/update-kh-by-id/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const data = req.body;
    const result = await KhachHang.findByIdAndUpdate(id, {
      AnhDaiDien: data.AnhDaiDien,
      TenKH: data.TenKH,
      TuoiKH: data.TuoiKH,
      DiaChi: data.DiaChi,
      GioiTinh: data.GioiTinh,
    });
    if (result) {
      res.json({
        status: 200,
        messenger: "tìm thấy id và update thành công",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        messenger: "update thất bại",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
  }
});



module.exports = router;