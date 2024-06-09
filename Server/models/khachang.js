const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const KhachHangSchema = new Schema({
  AnhDaiDien: { type: String, required: false },
  TenKH: { type: String, required: false },
  TuoiKH: { type: String, required: true },
  DiaChi: { type: String, required: true },
  GioiTinh: { type: String, required: true },
}, {
  timestamps: true
});

module.exports = mongoose.model('KhachHang', KhachHangSchema);
