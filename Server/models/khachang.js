const mongoose=require('mongoose');
const Scheme = mongoose.Schema;

const KhachHang = new Scheme({
    AnhDaiDien: {type:String, required: false},
    TenKH: {type:String, required: false},
    TuoiKH: {type: String, required:true},
    DiaChi: {type: String, required:true},
    GioiTinh: {type: String, required:true},
},{
    timestamps: true
})

module.exports = mongoose.model('khachhangs',KhachHang)